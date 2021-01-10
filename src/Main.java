import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.sql.SQLOutput;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

	public static String PATH = "..//javacore1.3.1//Games//savegames//";

	public static void main(String[] args) {

		openZip("zip.zip", PATH);
		deserialization(new String[]{"save1.dat", "save2.dat", "save3.dat"});
	}

	public static void openZip(String pathIn, String pathOut) {
		String path = PATH + pathIn;
		try (ZipInputStream zin = new ZipInputStream(new FileInputStream(path))) {
			ZipEntry entry;
			String name;
			long size;
			while ((entry = zin.getNextEntry()) != null) {
				name = entry.getName();
				size = entry.getSize();

				FileOutputStream fos = new FileOutputStream(PATH + name);
				for (int c = zin.read(); c != -1; c = zin.read()) {
					fos.write(c);
				}
				fos.flush();
				zin.closeEntry();
				fos.close();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void deserialization(String[] fileNames) {
		GameProgress gameProgress = null;

		for (int i = 0; i < fileNames.length; i++) {
			try (FileInputStream fis = new FileInputStream(PATH + fileNames[i]);
			     ObjectInputStream ois = new ObjectInputStream(fis)) {
				gameProgress = (GameProgress) ois.readObject();

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			System.out.println(gameProgress);
		}
	}
}
