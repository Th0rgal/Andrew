package io.th0rgal.andrew.chat;

import android.graphics.drawable.Drawable;

import java.util.Random;

public class Author {

    public String name;
    public String color;
    public Drawable drawable;

    public Author() {
        this.name = getRandomName();
        this.color = getRandomColor(name);
    }

    public Author(String name) {
        this.name = name;
        this.color = getRandomColor(name);
    }

    public Author(String name, Drawable avatar) {
        this.name = name;
        this.drawable = avatar;
    }

    private String getRandomName() {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (adjs[(int) Math.floor(Math.random() * adjs.length)] +
                "_" +
                nouns[(int) Math.floor(Math.random() * nouns.length)]);
    }

    private String getRandomColor(String authorName) {
        Random r = new Random(authorName.hashCode());
        StringBuilder sb = new StringBuilder("#");
        while (sb.length() < 7) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }
}
