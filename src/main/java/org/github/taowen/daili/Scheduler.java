package org.github.taowen.daili;

import kilim.Pausable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeoutException;

// kilim does not allow interface with Pausable
public abstract class Scheduler {

    public abstract void callSoon(Runnable task);

    public abstract void loop();

    public abstract SocketChannel accept(ServerSocketChannel serverSocketChannel, int timeout) throws IOException, Pausable, TimeoutException;

    public abstract int read(SocketChannel socketChannel, ByteBuffer byteBuffer, int timeout) throws IOException, Pausable, TimeoutException;

    public abstract int write(SocketChannel socketChannel, ByteBuffer byteBuffer, int timeout) throws IOException, Pausable, TimeoutException;

    public abstract void connect(SocketChannel socketChannel, SocketAddress remote, int timeout) throws IOException, TimeoutException, Pausable;

    public abstract SocketAddress receive(DatagramChannel datagramChannel, ByteBuffer byteBuffer, int timeout) throws IOException, TimeoutException, Pausable;

    public abstract int send(DatagramChannel channel, ByteBuffer byteBuffer, InetSocketAddress target, int timeout) throws IOException, TimeoutException, Pausable;
}
