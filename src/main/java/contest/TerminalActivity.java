package contest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TerminalActivity {

    private Runtime runtime;
    private File file;
    private String lang, pathToTests, response;
    private Map<String, String> compileInstructions;
    private Map<Integer, String> tasks;
    private Process startCode;
    private InputStream inputStream;
    private OutputStream outputStream;
    private int countOfTests, taskNumber;
    private volatile boolean isPassed, compilled;

    public TerminalActivity(File file, String lang, int taskNumber) throws IOException {
        compileInstructions = new HashMap<>();
        tasks = new HashMap<>();
        isPassed = true;
        tasks.put(1, "/Users/levinMK/IdeaProjects/Contester/src/main/resources/testCase/");
        compileInstructions.put("c++", "g++ -o studentSolution ");
        this.file = file;
        this.lang = lang;
        this.taskNumber = taskNumber;
        compilled = false;
        response = "";
    }

    public void compile() throws IOException {
        runtime = Runtime.getRuntime();
        startCode = runtime.exec("g++ -o studentSolution " + file.getName());
        Scanner processLog = new Scanner(startCode.getInputStream());
        while (processLog.hasNext()) System.out.println(processLog.nextLine());
        compilled = true;
    }

    public void test() throws IOException {
        while (!compilled) {

        }
        for (int i = 0; i < 3; i++) {
            File test = new File(tasks.get(taskNumber) + i + ".test");
            Scanner stdin = new Scanner(test);
            startCode = runtime.exec("./studentSolution");

            try (PrintWriter processCommunicator = new PrintWriter(startCode.getOutputStream())) {
                processCommunicator.write(stdin.nextLong() + " ");
                processCommunicator.write(stdin.nextLong() + " ");
                processCommunicator.write("\n");
            }

            Scanner check = new Scanner(startCode.getInputStream());

            StringBuilder studentAns = new StringBuilder();

            StringBuilder judgeAns = new StringBuilder();

            Scanner judge = new Scanner(new File(tasks.get(taskNumber) + i + ".ans"));

            while(judge.hasNext()){
                judgeAns.append(judge.nextLine());
            }

            judgeAns = new StringBuilder(judgeAns.toString().trim());

            while (check.hasNext()) {
                studentAns.append(check.nextLine());
            }
            studentAns = new StringBuilder(studentAns.toString().trim());

            if(judgeAns.toString().equals(studentAns.toString())){
                System.out.println("тест " + (i + 1) + " пройден");
                response += "тест " + (i + 1) + " пройден\n";
            }
            else {
                System.out.println("тест " + (i + 1) + " не пройден");
                response += "тест " + (i + 1) + " не пройден\n";
                isPassed = false;
                break;
            }
        }
        if(isPassed) {
            System.out.println("Полное решение!");
            response += "Полное решение!\n";
        }
    }

    public String getResponse(){
        return response;
    }

    public boolean checkTask(){
        return isPassed;
    }
}
