package icesi.ugoogle;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class NodoWritable implements Writable {

	private float pr;
	private String[] listaDeAdyacencia = new String[0];

	public NodoWritable() {
		super();
	}
	
	public float getPr() {
		return pr;
	}	

	public String[] getListaDeAdyacencia() {
		return listaDeAdyacencia;
	}

	public NodoWritable(String str) {
		super();
		String[] items = str.split(" ");
		this.pr = Float.parseFloat(items[0]);
		if (items.length == 2)
			this.listaDeAdyacencia = items[1].split("<;>");
	}

	public NodoWritable(float pr, String[] urlList) {
		super();
		this.pr = pr;
		this.listaDeAdyacencia = urlList;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		FloatWritable dw = new FloatWritable();
		ArrayWritable aw = new ArrayWritable(Text.class);
		dw.readFields(in);
		aw.readFields(in);
		pr = dw.get();
		listaDeAdyacencia = aw.toStrings();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		FloatWritable dw = new FloatWritable(pr);
		ArrayWritable aw = new ArrayWritable(listaDeAdyacencia);
		dw.write(out);
		aw.write(out);
	}

	public String toString() {
		String res = "";
		for (String s : listaDeAdyacencia) {
			res += s + "<;>";
		}
		return pr + " " + res;
	}
}
