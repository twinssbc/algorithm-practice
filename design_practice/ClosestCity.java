import java.util.*;

// Time: O(q * log(N))
// Space: O(N)
public class ClosestCity {
    private class City {
        int x;
        int y;
        String c;

        public City(int x, int y, String c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }

    public String[] process(int[] x, int[] y, String[] c, String[] query) {
        HashMap<Integer, List<City>> xCityMap = new HashMap<>();
        HashMap<Integer, List<City>> yCityMap = new HashMap<>();
        HashMap<String, City> cityMap = new HashMap<>();
        for (int i = 0; i < x.length; i++) {
            City city = new City(x[i], y[i], c[i]);
            cityMap.put(c[i], city);
            if (!xCityMap.containsKey(x[i])) {
                xCityMap.put(x[i], new ArrayList<>());
            }
            xCityMap.get(x[i]).add(city);
            if (!yCityMap.containsKey(y[i])) {
                yCityMap.put(y[i], new ArrayList<>());
            }
            yCityMap.get(y[i]).add(city);
        }

        for (Map.Entry<Integer, List<City>> entry : xCityMap.entrySet()) {
            entry.getValue().sort((c1, c2) -> c1.y - c2.y);
        }
        for (Map.Entry<Integer, List<City>> entry : yCityMap.entrySet()) {
            entry.getValue().sort((c1, c2) -> c1.x - c2.x);
        }

        String[] answers = new String[query.length];

        int i = 0;
        for (String q : query) {
            City searchCity = cityMap.get(q);
            if (searchCity == null) {
                answers[i] = "None";
            } else {
                List<City> candidateCities = new ArrayList<>();
                List<City> searchList = xCityMap.get(searchCity.x);
                int yIndex = search(searchList, 1, searchCity.y);
                if (yIndex - 1 >= 0) {
                    candidateCities.add(searchList.get(yIndex - 1));
                }
                if (yIndex + 1 < searchList.size()) {
                    candidateCities.add(searchList.get(yIndex + 1));
                }
                searchList = yCityMap.get(searchCity.y);
                int xIndex = search(searchList, 0, searchCity.x);
                if (xIndex - 1 >= 0) {
                    candidateCities.add(searchList.get(xIndex - 1));
                }
                if (xIndex + 1 < searchList.size()) {
                    candidateCities.add(searchList.get(xIndex + 1));
                }

                int minDistance = Integer.MAX_VALUE;
                List<City> minCities = new ArrayList<>();
                for (City candidateCity : candidateCities) {
                    int distance = Math.abs(candidateCity.x - searchCity.x) + Math.abs(candidateCity.y - searchCity.y);
                    if (minDistance > distance) {
                        minCities.clear();
                        minCities.add(candidateCity);
                        minDistance = distance;
                    } else if (minDistance == distance) {
                        minCities.add(candidateCity);
                    }
                }

                minCities.sort((c1, c2) -> c2.c.compareTo(c1.c));
                if (minCities.size() > 0) {
                    answers[i] = minCities.get(0).c;
                } else {
                    answers[i] = "NONE";
                }
            }
            i++;
        }
        return answers;
    }

    // mode: 0, search x; 1, search y
    private int search(List<City> searchList, int mode, int target) {
        int l = 0;
        int r = searchList.size() - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            int checkValue;
            if (mode == 0) {
                checkValue = searchList.get(mid).x;
            } else {
                checkValue = searchList.get(mid).y;
            }
            if (checkValue == target) {
                return mid;
            } else if (checkValue < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        ClosestCity closestCity = new ClosestCity();
        String[] results = closestCity.process(new int[]{3, 4, 2, 2, 10}, new int[]{1, 1, 1, 2, 3, 10}, new String[]{"c1", "c2", "c3", "c4", "c5"}, new String[]{"c1", "c2", "c3", "c4", "c5"});
        System.out.println(Arrays.toString(results));
    }
}
