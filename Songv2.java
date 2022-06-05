package application;

public class Songv2 {
	String title = "Parostatek";
	int tempo = 1250;
	String rhythm = "6/6";
	String album = "Rysunek na szkle";
	String performer = "Krzysztof Krawczyk";
	String text = "W starym albumie u mego dziadka\n" +
			"Jest takie zdjecie, istny cud\n" +
			"P³yn¹cy w falach, wœród mewek stadka";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		 this.tempo = tempo;
	}

	public String getRhythm() {
		return rhythm;
	}

	public void setRhythm(String rhythm) {
		this.rhythm = rhythm;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getPerformer() {
		return performer;
	}

	public void setPerformer(String performer) {
		this.performer = performer;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
