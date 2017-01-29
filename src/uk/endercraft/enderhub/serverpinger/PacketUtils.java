package uk.endercraft.enderhub.serverpinger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

class PacketUtils {
	public static final Charset UTF16BE = Charset.forName("UTF-16BE");
	public static final Charset UTF8 = Charset.forName("UTF-8");

	public static void a(DataOutputStream out, String s) throws IOException {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[(i / 2)] = ((byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16)));
		}
		out.write(data);
	}

	public static void writeString(DataOutputStream out, String s, Charset charset) throws IOException {
		if (charset == UTF8) {
			writeVarInt(out, s.length());
		} else {
			out.writeShort(s.length());
		}
		out.write(s.getBytes(charset));
	}

	public static int readVarInt(DataInputStream in) throws IOException {
		int i = 0;
		int j = 0;
		for (;;) {
			int k = in.readByte();
			i |= (k & 0x7F) << j++ * 7;
			if (j > 5) {
				throw new RuntimeException("VarInt too big");
			}
			if ((k & 0x80) != 128) {
				break;
			}
		}
		return i;
	}

	public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
		for (;;) {
			if ((paramInt & 0xFFFFFF80) == 0) {
				out.write(paramInt);
				return;
			}
			out.write(paramInt & 0x7F | 0x80);
			paramInt >>>= 7;
		}
	}
}
