package com.example.facedetection.dummy;

import com.example.facedetection.data.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class StudentContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Student> ITEMS = new ArrayList<Student>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Student> ITEM_MAP = new HashMap<String, Student>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Student item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId()+"", item);
    }

    private static Student createDummyItem(int position) {
        String url1 = "https://cms.xitek.com/uploads/allimg/181229/7577_29110205_1.jpg";
        String url2 = "https://cms.xitek.com/uploads/allimg/181229/7577_29104601_1.jpg";
        String avatar = url1;
        if (position % 2 == 0) {
            avatar = url2;
        }
        return new Student(position, "李帆", position%2+1, avatar);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}

