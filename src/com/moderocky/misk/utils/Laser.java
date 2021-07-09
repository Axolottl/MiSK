package com.moderocky.misk.utils;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.UUID;

/**
 * A whole class to create Guardian Beams by reflection </br>
 * Inspired by the API <a href="https://www.spigotmc.org/resources/guardianbeamapi.18329">GuardianBeamAPI</a></br>
 * <b>1.9 -> 1.14</b>
 *
 * @see <a href="https://github.com/SkytAsul/GuardianBeam">GitHub page</a>
 * @author SkytAsul
 */
public class Laser {
    private final int duration;
    private final int distanceSquared;
    private final int squid;
    private final int guardian;

    private final Location start;
    private final Location end;
    private final Object createGuardianPacket;
    private final Object createSquidPacket;
    private final Object destroyPacket;

    private BukkitRunnable run;

    /**
     * Create a Laser instance
     * @param start Location where laser will starts
     * @param end Location where laser will ends
     * @param duration Duration of laser in seconds (<i>-1 if infinite</i>)
     * @param distance Distance where laser will be visible
     */
    public Laser(Location start, Location end, int duration, int distance) throws ReflectiveOperationException {
        this.start = start;
        this.end = end;
        this.duration = duration;
        distanceSquared = distance*distance;

        createSquidPacket = Packets.createPacketSquidSpawn(end);
        squid = (int) Packets.getField(Packets.packetSpawn, "a", createSquidPacket);
        createGuardianPacket = Packets.createPacketGuardianSpawn(start, squid);
        guardian = (int) Packets.getField(Packets.packetSpawn, "a", createGuardianPacket);

        destroyPacket = Packets.createPacketRemoveEntities(squid, guardian);
    }

    public void start(Plugin plugin){
        Validate.isTrue(run == null, "Task already started");
        run = new BukkitRunnable() {
            int time = duration;
            HashSet<Player> show = new HashSet<>();
            @Override
            public void run() {
                try {
                    if (time == 0){
                        cancel();
                        return;
                    }
                    for (Player p : start.getWorld().getPlayers()) {
                        if (isCloseEnough(p.getLocation()) && !show.contains(p)) {
                            sendStartPackets(p);
                            show.add(p);
                        } else if (show.contains(p)) {
                            Packets.sendPacket(p, destroyPacket);
                            show.remove(p);
                        }
                    }
                    if (time != -1) time--;
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public synchronized void cancel() throws IllegalStateException {
                super.cancel();
                try {
                    for (Player p : show) {
                        Packets.sendPacket(p, destroyPacket);
                    }
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
                run = null;
            }
        };
        run.runTaskTimer(plugin, 0L, 20L);
    }

    public void stop() {
        Validate.isTrue(run != null, "Task not started");
        run.cancel();
    }

    public boolean isStarted() {
        return run != null;
    }

    private void sendStartPackets(Player p) throws ReflectiveOperationException {
        Packets.sendPacket(p, createSquidPacket);
        Packets.sendPacket(p, createGuardianPacket);
    }

    private boolean isCloseEnough(Location location) {
        return start.distanceSquared(location) <= distanceSquared ||
                end.distanceSquared(location) <= distanceSquared;
    }



    private static class Packets {
        private static int lastIssuedEID = 2000000000;
        static int generateEID() {
            return lastIssuedEID++;
        }
        private static int      version = Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].substring(1).split("_")[1]);
        private static String   npack   = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        private static String   cpack   = Bukkit.getServer().getClass().getPackage().getName() + ".";
        private static Object   fakeSquid;
        private static Method   watcherSet;
        private static Method   watcherRegister;
        private static Class<?> packetSpawn;
        private static String   watcherSet1;
        private static String   watcherSet2;
        private static String   watcherSet3;
        private static int      squidID;
        private static int      guardianID;
        static {
            try {
                if (version < 13) {
                    watcherSet1 = "Z";
                    watcherSet2 = "bA";
                    watcherSet3 = "bB";
                    squidID     = 94;
                    guardianID  = 68;
                } else if (version == 13) {
                    watcherSet1 = "ac";
                    watcherSet2 = "bF";
                    watcherSet3 = "bG";
                    squidID     = 70;
                    guardianID  = 28;
                } else if (version > 13) {
                    watcherSet1 = "W";
                    watcherSet2 = "b";
                    watcherSet3 = "bD";
                    squidID     = 73;
                    guardianID  = 30;
                }
                // Fix for Paper -42 +
                Object world    = Class.forName(cpack + "CraftWorld").getDeclaredMethod("getHandle").invoke(Bukkit.getWorlds().get(0));
                Object[] entityConstructorParams = version < 14 ? new Object[]{world} : new Object[]{Class.forName(npack + "EntityTypes").getDeclaredField("SQUID").get(null), world};
                // Unfix
                fakeSquid       = Class.forName(cpack + "entity.CraftSquid").getDeclaredConstructors()[0].newInstance(
                       null, Class.forName(npack + "EntitySquid").getDeclaredConstructors()[0].newInstance(
                                entityConstructorParams));
                watcherSet      = getMethod(Class.forName(npack + "DataWatcher"), "set");
                watcherRegister = getMethod(Class.forName(npack + "DataWatcher"), "register");
                packetSpawn     =           Class.forName(npack + "PacketPlayOutSpawnEntityLiving");
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }

        public static void sendPacket(Player p, Object packet) throws ReflectiveOperationException {
            Object entityPlayer     = Class.forName(cpack + "entity.CraftPlayer").getDeclaredMethod("getHandle").invoke(p);
            Object playerConnection = entityPlayer.getClass().getDeclaredField("playerConnection").get(entityPlayer);
            playerConnection.getClass().getDeclaredMethod("sendPacket", Class.forName(npack + "Packet")).invoke(playerConnection, packet);
        }
        public static Object createPacketSquidSpawn(Location location) throws ReflectiveOperationException {
            Object packet = packetSpawn.newInstance();
            setField(packet, "a", generateEID());
            setField(packet, "b", UUID.randomUUID());
            setField(packet, "c", squidID);
            setField(packet, "d", location.getX());
            setField(packet, "e", location.getY());
            setField(packet, "f", location.getZ());
            setField(packet, "j", (byte) (location.getYaw() * 256.0F / 360.0F));
            setField(packet, "k", (byte) (location.getPitch() * 256.0F / 360.0F));
            Object nentity = fakeSquid.getClass().getDeclaredMethod("getHandle").invoke(fakeSquid);
            Object watcher = Class.forName(npack + "Entity").getDeclaredMethod("getDataWatcher").invoke(nentity);
            watcherSet.invoke(watcher, getField(Class.forName(npack + "Entity"), watcherSet1, null), (byte) 32);
            setField(packet, "m", watcher);
            return packet;
        }
        public static Object createPacketGuardianSpawn(Location location, int entityId) throws ReflectiveOperationException {
            Object packet = packetSpawn.newInstance();
            setField(packet, "a", generateEID());
            setField(packet, "b", UUID.randomUUID());
            setField(packet, "c", guardianID);
            setField(packet, "d", location.getX());
            setField(packet, "e", location.getY());
            setField(packet, "f", location.getZ());
            setField(packet, "j", (byte) (location.getYaw() * 256.0F / 360.0F));
            setField(packet, "k", (byte) (location.getPitch() * 256.0F / 360.0F));
            Object nentity = fakeSquid.getClass().getDeclaredMethod("getHandle").invoke(fakeSquid);
            Object watcher = Class.forName(npack + "Entity").getDeclaredMethod("getDataWatcher").invoke(nentity);
            watcherSet.invoke(watcher, getField(Class.forName(npack + "Entity"), watcherSet1, null), (byte) 32);
            try {
                watcherSet.invoke(watcher, getField(Class.forName(npack + "EntityGuardian"), watcherSet2, null), false);
            } catch (InvocationTargetException ex) {
                watcherRegister.invoke(watcher, getField(Class.forName(npack + "EntityGuardian"), watcherSet2, null), false);
            }
            try {
                watcherSet.invoke(watcher, getField(Class.forName(npack + "EntityGuardian"), watcherSet3, null), entityId);
            } catch (InvocationTargetException ex) {
                watcherRegister.invoke(watcher, getField(Class.forName(npack + "EntityGuardian"), watcherSet3, null), entityId);
            }
            setField(packet, "m", watcher);
            return packet;
        }
        public static Object createPacketRemoveEntities(int squidId, int guardianId) throws ReflectiveOperationException {
            Object packet = Class.forName(npack + "PacketPlayOutEntityDestroy").newInstance();
            setField(packet, "a", new int[]{squidId, guardianId});
            return packet;
        }
        private static Method getMethod(Class<?> clazz, String name){
            for (Method m : clazz.getDeclaredMethods()){
                if (m.getName().equals(name)) return m;
            }
            return null;
        }
        private static void setField(Object instance, String name, Object value) throws ReflectiveOperationException{
            Validate.notNull(instance);
            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        }
        private static Object getField(Class<?> clazz, String name, Object instance) throws ReflectiveOperationException{
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field.get(instance);
        }
    }
}
