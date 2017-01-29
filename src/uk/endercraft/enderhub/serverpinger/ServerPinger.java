package uk.endercraft.enderhub.serverpinger;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public final class ServerPinger {

	private static ServerPinger instance;

	private static final Gson gson = new Gson();

	public StatusResponse fetchData(String host, int port) {
		Socket socket = null;
		DataOutputStream dout = null;
		DataInputStream din = null;
		try {
			socket = new Socket(host, port);
			socket.setSoTimeout(2000);
			dout = new DataOutputStream(socket.getOutputStream());
			din = new DataInputStream(socket.getInputStream());
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			DataOutputStream handshake = new DataOutputStream(bOut);
			handshake.write(0);
			PacketUtils.writeVarInt(handshake, 4);
			PacketUtils.writeString(handshake, host, PacketUtils.UTF8);
			handshake.writeShort(port);
			PacketUtils.writeVarInt(handshake, 1);
			byte[] b = bOut.toByteArray();
			PacketUtils.writeVarInt(dout, b.length);
			dout.write(b);
			b = new byte[] { 0 };
			PacketUtils.writeVarInt(dout, b.length);
			dout.write(b);

			PacketUtils.readVarInt(din);
			PacketUtils.readVarInt(din);
			byte[] responseData = new byte[PacketUtils.readVarInt(din)];
			din.readFully(responseData);
			String jsonString = new String(responseData, PacketUtils.UTF8);
			return (StatusResponse) gson.fromJson(jsonString, StatusResponse.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dout != null) {
				try {
					dout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (din != null) {
				try {
					din.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static ServerPinger get() {
		if (instance == null)
			instance = new ServerPinger();
		return instance;
	}

}
