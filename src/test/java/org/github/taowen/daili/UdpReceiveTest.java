package org.github.taowen.daili;

import kilim.Pausable;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class UdpReceiveTest extends UsingFixture {
    public void test() throws Exception {
        Scheduler scheduler = new TestScheduler(this);
        DailiTask task = new DailiTask(scheduler) {
            @Override
            public void execute() throws Pausable, Exception {
                DatagramChannel channel = DatagramChannel.open();
                channel.configureBlocking(false);
                channel.socket().bind(new InetSocketAddress(9090));
                ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                scheduler.receive(channel, buffer);
                ByteBuffer expected = ByteBuffer.wrap(new byte[]{1, 2, 3, 4});
                exit(buffer.flip().equals(expected));
            }
        };
        scheduler.callSoon(task);
        scheduler.loopOnce();
        DatagramSocket client = new DatagramSocket();
        client.connect(new InetSocketAddress(9090));
        assertTrue(client.isConnected());
        scheduler.loopOnce();
        scheduler.loopOnce();
        client.send(new DatagramPacket(new byte[]{1, 2, 3, 4}, 4));
        scheduler.loopOnce();
        scheduler.loopOnce();
        assertTrue((Boolean)task.exitResult);
    }
}
