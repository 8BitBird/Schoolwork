package assign5;

import java.io.Serializable;

/**
  CS3354 Spring 2019 Review Class Implementation
    @author metsis
    @author tesic
    @author wen
    @author webber
 */
public class MovieReview implements Serializable {

    /**
     * Constructor.
     * @param id
     * @param text
     * @param realPolarity 
     */
    public MovieReview(int id, String text, int realPolarity) {
        this.id = id;
        this.text = text;
        this.realPolarity = realPolarity;
        this.predictedPolarity = 0; // Set a default value. To be changed later.
    }


    /**
     *
     * @return Tweet id field
     */
    public int getId() {
        return id;
    }


    /**
     *
     * @return Tweet text field
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @return predictedPolarity field
     */
    public int getPredictedPolarity() {
        return predictedPolarity;
    }

    /**
     *
     * @param predictedPolarity
     */
    public void setPredictedPolarity(int predictedPolarity) {
        this.predictedPolarity = predictedPolarity;
    }

    /**
     * 
     * @return realPolarity
     */
    public int getRealPolarity() {
        return realPolarity;
    }

    
    /**
     * The id of the review (e.g. 2087).
     */
    private final int id;
    
    /**
     *  The text of the review.
     */
    private final String text;
    
    /**
     * The predicted polarity of the tweet (0 = negative, 1 = positive).
     */
    private int predictedPolarity;
    
    /**
     * The ground truth polarity of the tweet (0 = negative, 1 = positive, 2 = unknown).
     */
    private final int realPolarity;

}
