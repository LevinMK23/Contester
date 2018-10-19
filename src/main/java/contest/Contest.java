package contest;

import java.util.concurrent.atomic.AtomicLong;

public class Contest {

    private long id;
    private String content;

    public Contest(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
