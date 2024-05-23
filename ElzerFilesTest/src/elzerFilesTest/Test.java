package elzerFilesTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Iterator;

import fileAccess.IniFile;
import fileAccess.ScriptFile;

public class Test {

	public Test() {

		IniFile ini = new IniFile("Elzer.conf");
		System.out.println(ini.getFileName());
		try {
			ini.readIniFile();

			ini.setSection("configuracion");
			System.out.println(ini.getParameterStr("tituloAplicacion"));
			System.out.println(ini.getParameterStr("nombresPanelesTamanoFuente"));

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ScriptFile fichero = new ScriptFile("ElzerTrenes.conf");
		try {
			if (fichero.isExistFile()) {
				fichero.readScriptFile();
				Iterator<String> lista = fichero.readScriptFile().iterator();
				while (lista.hasNext()) {
					String string = (String) lista.next();
					System.out.println(string);
				}
			}else {
				System.out.println("No existe el fichero SCRIPT");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		IniFile ini2 = new IniFile("Otro.conf");
		if (ini2.isFileError()) {
			System.out.println("No existe");
		}
		ini2.addSection("PEPE001");
		ini2.addParameter("primero", 123);
		ini2.addParameter("segundo", 123);
		ini2.addParameter("tercero", 123);
		ini2.addSection("PEPE002");
		ini2.addParameter("primero", 123);
		try {
			ini2.writeIniFile(true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		new Test();
	}

}
