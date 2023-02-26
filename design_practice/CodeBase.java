import java.util.*;

public class CodeBase {
    public List<String> process(Map<String, Integer> codeBases, Map<String, Set<String>> dependencies) {
        // construct dependencies
        HashMap<String, List<String>> afterTaskMap = new HashMap<>();
        for(Map.Entry<String, Set<String>> dependency: dependencies.entrySet()) {
            String task = dependency.getKey();
            Set<String> preList = dependency.getValue();
            for(String preTask: preList) {
                if(!afterTaskMap.containsKey(preTask)) {
                    afterTaskMap.put(preTask, new ArrayList<>());
                }
                afterTaskMap.get(preTask).add(task);
            }
        }

        Queue<String> noDepTasks = new LinkedList<>();
        for(String task: codeBases.keySet()) {
            if(!dependencies.containsKey(task)) {
                noDepTasks.offer(task);
            }
        }

        List<String> answers = new ArrayList<>();
        while(!noDepTasks.isEmpty()) {
            List<String> toClearTasks = new ArrayList<>();
            int minCount = Integer.MAX_VALUE;
            while(!noDepTasks.isEmpty()) {
                String taskName = noDepTasks.poll();
                minCount = Math.min(minCount, codeBases.get(taskName));
                toClearTasks.add(taskName);
            }

            StringBuilder sb = new StringBuilder();
            sb.append(minCount);
            sb.append(",");
            for(String toClearTask:toClearTasks) {
                sb.append(toClearTask);
                sb.append(",");
                int leftAmount = codeBases.get(toClearTask) - minCount;
                if(leftAmount == 0) {
                    // remove prereq task
                    if(afterTaskMap.containsKey(toClearTask)) {
                        for(String afterTask: afterTaskMap.get(toClearTask)) {
                            dependencies.get(afterTask).remove(toClearTask);
                            if(dependencies.get(afterTask).size() == 0) {
                                noDepTasks.add(afterTask);
                            }
                        }
                    }
                } else {
                    noDepTasks.add(toClearTask);
                    codeBases.put(toClearTask, leftAmount);
                }
            }
            answers.add(sb.toString());
        }
        return answers;
    }

    public static void main(String[] args) {
        CodeBase cb = new CodeBase();
        Map<String, Integer> tasks = new HashMap<>();
        tasks.put("A", 1);
        tasks.put("B", 2);
        tasks.put("C", 3);
        tasks.put("D", 3);
        tasks.put("E", 3);

        Map<String, Set<String>> dependencies = new HashMap<>();
        populateDep(dependencies, "B:A");
        populateDep(dependencies, "C:A");
        populateDep(dependencies, "D:A,B");
        populateDep(dependencies, "E:B,C");

        List<String> answers = cb.process(tasks, dependencies);
        for(String s : answers) {
            System.out.println(s);
        }
    }

    private static void populateDep(Map<String, Set<String>> dependencies, String s) {
        String[] segments = s.split(":");
        String task = segments[0];
        String[] deps = segments[1].split(",");
        Set<String> depTasks = new HashSet<>(Arrays.asList(deps));
        dependencies.put(task, depTasks);
    }
}
