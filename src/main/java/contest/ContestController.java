package contest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ContestController {

    private AtomicLong counter = new AtomicLong(0);
    private TerminalActivity activity;
    private File file;
    private boolean f;
    private String prev, post;

    @RequestMapping("/contest")
    public Contest contest(
            @RequestParam(value = "task", defaultValue = "null")
                    String task) throws IOException {
        file = new File("task" + (counter.get() + 1) + ".cpp");
        
        prev += task + "\n";
        if(!file.exists()) {
            f = file.createNewFile();
        }
        try(PrintWriter writer = new PrintWriter(file)){
            writer.write(task);
            writer.write("\n");
        }
        activity = new TerminalActivity(file, "c++", 1);
        activity.compile();
        activity.test();
        if(activity.checkTask()){
            return new Contest(counter.incrementAndGet(),
                    "Задача решена полностью! " + activity.getResponse());
        }
        else return new Contest(counter.incrementAndGet(),
                 "Задача не решена! " + activity.getResponse());
    }

}
