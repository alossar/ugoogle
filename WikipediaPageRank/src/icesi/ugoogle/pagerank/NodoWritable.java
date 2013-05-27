package icesi.ugoogle.pagerank;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Writable;

public class NodoWritable implements Writable {

	private double pageRank;
	private List<String> listaDeAdyacencia = new ArrayList<String>();

	public NodoWritable() {
		super();
	}

	public double getPageRank() {
		return pageRank;
	}

	public List<String> getListaDeAdyacencia() {
		return listaDeAdyacencia;
	}

	public NodoWritable(double pr, List<String> urlList) {
		super();
		this.pageRank = pr;
		this.listaDeAdyacencia = urlList;
	}

	public NodoWritable(String str) {
		super();
		String[] items = str.split(" ");
		this.pageRank = Double.parseDouble(items[0]);
		if (items.length == 2) {
			String[] arreglo = items[1].split("<;>");
			listaDeAdyacencia.clear();
			for (String string : arreglo) {
				listaDeAdyacencia.add(string);
			}
		}
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		pageRank = in.readDouble();
		int size = in.readInt();
		listaDeAdyacencia = new ArrayList<String>(size);
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				listaDeAdyacencia.add(in.readUTF());
			}
		}
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeDouble(pageRank);
		out.writeInt(listaDeAdyacencia.size());
		if (listaDeAdyacencia.size() > 0) {
			for (String l : listaDeAdyacencia) {
				out.writeUTF(l);
			}
		}
	}

	// @Override
	// public String toString() {
	// String res = "";
	// for (String s : listaDeAdyacencia) {
	// res += s + "<;>";
	// }
	// return pageRank + " " + res;
	// }
}
