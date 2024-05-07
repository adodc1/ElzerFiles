package fileAccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import fileAccess.filesScript.FileScriptAccess;

public class ScriptFile {

	private final FileScriptAccess file;

	public ScriptFile(String fileName) {
		this.file = new FileScriptAccess(fileName);
	}

	public boolean isExistFile() {
		return !this.file.isError();
	}

	public ArrayList<String> readScriptFile() throws IOException, FileNotFoundException {
		if (!this.file.isExist()) {
			return this.file.readAll();
		} else {
			throw new FileNotFoundException(this.getClass().getCanonicalName()
					+ ".readScriptFile: Fichero no encontrado " + this.file.getFileName());
		}
	}

	public void writeScriptFile(ArrayList<String> listData, boolean encrypto) throws IOException {
		this.file.writeAll(listData, encrypto);
	}

}
