package retroindex;

/**
 * Created by Salah on 06/11/2015.
 */
public class Document {
    private String url;
    private double frequency;

    public Document(String url, double frequency)
    {
        this.url = url;
        this.frequency = frequency;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
}
