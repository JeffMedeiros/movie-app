package br.com.training.movie_manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DateRange {
    @SerializedName("maximum")
    @Expose
    private String maximum;

    @SerializedName("minimum")
    @Expose
    private String minimum;

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    /**
     * Convert Object to json format.
     *
     * @return String
     */
    public String toJson() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return gson.toJson(this);
    }

    /**
     * Convert json to Object.
     *
     * @param json String
     * @return DateRange
     */
    public static DateRange jsonDeserialize(String json) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        Type typeDates = new TypeToken<DateRange>() { }.getType();

        return gson.fromJson(json, typeDates);
    }
}
