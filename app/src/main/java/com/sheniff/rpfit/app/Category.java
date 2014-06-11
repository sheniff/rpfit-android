package com.sheniff.rpfit.app;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sheniff on 6/11/14.
 */
public class Category {

    private String id;
    private String name;
    private int xp;
    private int currentLevel;
    private int currentXp;
    private int currentPerc;

    public Category(JSONObject json) throws JSONException {
        id = json.getString("id");
        name = json.getJSONObject("category").getString("name");
        xp = json.getInt("xp");

        this.computeCurrentStatus();
    }

    private void computeCurrentStatus() {
        int level = 0;
        int exp = this.xp;

        while (exp - UIUtils.LEVELS[level] > 0) {
            exp -= UIUtils.LEVELS[level];
            level++;
        }

        this.currentLevel = level;
        this.currentXp = exp;
        this.currentPerc = Math.round( exp / UIUtils.LEVELS[level] * 100 );
    }

    public String getName() {
        return this.name;
    }

    public int getXp() {
        return this.xp;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public int getCurrentXp() {
        return this.currentXp;
    }

    public int getCurrentPerc() {
        return this.currentPerc;
    }
}
