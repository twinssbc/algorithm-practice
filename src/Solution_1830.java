import java.util.HashMap;
import java.util.Map;

class Solution_1830 {
    private final static int MOD = 1000_000_007;
    private final static Map<Integer, Long> factorMemo = new HashMap<>();

    public int makeStringSorted(String s) {
        int[] charCount = new int[26];
        //count all occurrence
        for (int i = 0; i < s.length(); i++) {
            charCount[s.charAt(i) - 'a']++;
        }

        long count = 0;
        for (int i = 0; i < s.length(); i++) {
            //for each character before c character, count the permutation starting with that character
            //it equals to the (full permutation number) * (number of characters before c) / (total number of characters)
            long allPossibilities = permCount(charCount, s.length() - i);
            char c = s.charAt(i);
            int countBeforeC = 0;
            for (int j = 0; j < c - 'a'; j++) {
                countBeforeC += charCount[j];
            }
            long currentResult = modDiv((allPossibilities * countBeforeC) % MOD, s.length() - i, MOD);
            count = (count + currentResult) % MOD;
            charCount[c - 'a']--;
        }

        return (int) count;
    }

    public long modFactorial(int n, int mod) {
        //Use memo to avoid TLE
        if (factorMemo.containsKey(n)) {
            return factorMemo.get(n);
        }
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result = (result * i) % mod;
        }
        factorMemo.put(n, result);
        return result;
    }

    public long modPower(long base, int exponent, int mod) {
        if (mod == 1) {
            return 0;
        }
        long result = 1;
        base = base % mod;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % mod;
            }
            exponent = exponent >> 1;
            base = (base * base) % mod;
        }
        return result;
    }

    // (a/b) % n = (a * 1/b) % n = (a * b^-1) % n = (a % n * b^-1 % n) % n =  (a % n * (b^(n-2)) % n) % n
    public long modDiv(long a, long b, int mod) {
        b = modPower(b, mod - 2, mod);
        return ((a % mod) * b) % mod;
    }

    public long permCount(int[] charCount, int totalCount) {
        long total = modFactorial(totalCount, MOD);

        //divide by repeated number
        for (int i = 0; i < 26; i++) {
            if (totalCount == 0) {
                break;
            }
            if (charCount[i] > 1) {
                total = modDiv(total, modFactorial(charCount[i], MOD), MOD);
            }
            totalCount -= charCount[i];
        }
        return total;
    }

    public static void main(String[] args) {
        Solution_1830 solution = new Solution_1830();
        String s = "cba";
        long answer = solution.makeStringSorted(s);
        assert answer == 5 : "cba";

        String s0 = "aabaa";
        long answer0 = solution.makeStringSorted(s0);
        assert answer0 == 2 : "aabaa";

        String s01 = "daaa";
        long answer01 = solution.makeStringSorted(s01);
        assert answer01 == 3 : "daaa";

        String s1 = "leetcodeleetcodeleetcode";
        long answer1 = solution.makeStringSorted(s1);
        assert answer1 == 982157772 : "leetcodeleetcodeleetcode";

        String s2 = "jensgfyynhtwlgnoxkkkiguizadmz";
        long answer2 = solution.makeStringSorted(s2);
        assert answer2 == 612956436 : "jensgfyynhtwlgnoxkkkiguizadmz";

        String s3 = "ymgsbciztexuxzqbovpclfqgslparmfdbrvxkaofpgkxzuzqxwuufipqrjkenmwckyqpuuxdbhqvqqcttzwbgqdtgymzbjdqtjgnzsfjvrgssfkfptchdocgtsfagdvtokppimtejhwcakrjmkrwprqshuzclzzwtavprjcpagumnzskrmgtmgjflohnhrzsfaihybozbkbngzntyteojjbkrrpzpbqseqckgmijudebwkxgcvcwblymwskncrrmmhhpkveeccchdthzrbbxfpgxlgrsusmiadfrzlyrhjuayiovqoicfnamvzabblyttbqnennrohvosumykxskricxlgqyefckkwjiadisuycjxguhcgl";
        long answer3 = solution.makeStringSorted(s3);
        assert answer3 == 22363296 : "ymgsbciztexuxzqbovpclfqgslparmfdbrvxkaofpgkxzuzqxwuufipqrjkenmwckyqpuuxdbhqvqqcttzwbgqdtgymzbjdqtjgnzsfjvrgssfkfptchdocgtsfagdvtokppimtejhwcakrjmkrwprqshuzclzzwtavprjcpagumnzskrmgtmgjflohnhrzsfaihybozbkbngzntyteojjbkrrpzpbqseqckgmijudebwkxgcvcwblymwskncrrmmhhpkveeccchdthzrbbxfpgxlgrsusmiadfrzlyrhjuayiovqoicfnamvzabblyttbqnennrohvosumykxskricxlgqyefckkwjiadisuycjxguhcgl";

        String s4 = "xrgcgvfegxyrewflmuxsdqlccssbyshkzdoskkxgugukaxtsfvrdzxipngydewvizdbhbmsdatikrwmphvygtsoctbvibfrjnucbqigmjvsskhjflokpabaweqkqwvlzaufnpbxxzjuiksmrghfhjfshbyfejuvhmsyjtnabwacoamsljpsiufnfmjrzyutrljvwpdmmjkucrixisxmzxskunvvlkoypdxdihwahmnlymvmtayxelraszgikogwftfuzmzpsvduxfveghagabozgufkchyxlaxdqprlzndhxigsqgtzzdzjkpqczvnddnowkzebtmdtbfgflgahlmjlukhppmvabzurtesbplximnuveebcwxodplltnufygqlnkebzhgicqujdsdpmzfiiucopshgohlvstrseohvfppbvpbcpwetxrfxezvgdondfuvzjybsxxsdimaewgjxovskbgprwjbprjdmyiojzhaqwktjlsuqrgecrzksroqlrbdyyswfbxrhwxxwcicqroxutifqfsstffpmppbcwcbwqqewppgadncagtcjxcsbzwsfznnlwnojelfjrgnjpjrruzdkwbmndaeijlstpbnguzbwfgbqssnhivimqeqzknpbysgpbtjhoxhvjyodgtirhkycwjcwevnfxbcmvktvuifqhjhbqgsgnevmbojokwrrbvtaqgfaawsnnnpbebnhcucypoimwbctrnylxhhpcaeynrakuocvjfptvgduzyfcdbwkmzbhzgmftruobpufbydwhypmzimuulckaeslwqwrraxytyjprhxyltptevqhnwqpntfxcrmknmyryrlmrvfaphysxjgjistjmdttekcicctxquxljnmeufkgjlwonolziivzcrhpshxxyzzhojrzpjlkjcoxzqlvlqahdqrboviwasdphnkcpsmprorsvvblltdfliltrqppxzaabvqbkkzebsvwvlmwbumpadvgnumxulximqaqxplrnevrylwqpglxnplcnxkbkxcvivteanlhqxpjwwdsexjpvwfkewzubudjoearteslvszqielzsszcfiukapjbhuolvgnefywpttuotnemakptqovomhqjsymevljbjegvnuzzxuhcjkmxavpzkhwrbhktphaeytmwciczggbhegvexjjjovrwynrfwkuxxyvadhsqyhzzmuypqvfdivyzilnfczleiqwtepwvioclvgvtfqleozkhkcpcndmdbjsesusazyxhgggxzbqzpfdpsypkclixzcydvguicydgnhftaafetkzbyqcqxenyubamlnwwjgxxgntrpqhvmdpzguflnouezlhoezrvbedufughsbpzowaygxezrfxlkagmpaszrgkccigsitphxqntswcmhwvluarbaqpbxyuetzhramjttkyiigwojjwndhwkzylangnkzfhbuvwawqmsnjqmgnmkdfvlaecjrdfbshpkuywexeqwounujltfeapjwyjdlnltalmdfonljnatlhekfbahdkvzhnqcldpdiqqmkfmrfubsbhkrzmoivhtresyerneiegindonnbplqobzfrpeobpbcdqbyksxgzhcscnsmvkgefqtiujkhpmovyovfwkcsmtrpcvsaopvhrfweudtjzcaycsclyazigoecyjeuwkeqfsgvhgdcgpkbxtnambukugqhcfpwkdnmsaodecrbcjdnmwtajnwqirqdhpnlrcelhoohxjhmlvcdvybzdlwxhtyyzbsypcyqjtksztokjnsplizblimzhwoiwlilgpsonwjlxanfpscctwrpvizrvqanzwffknpvulpotlnpvivggunulremgjszyxbauhqomwnilybptuymtwlfoszmvaeocynlgcopjfmmhzmapffe";
        long answer4 = solution.makeStringSorted(s4);
        assert answer4 == 915030775 : "ymgsbciztexuxzqbovpclfqgslparmfdbrvxkaofpgkxzuzqxwuufipqrjkenmwckyqpuuxdbhqvqqcttzwbgqdtgymzbjdqtjgnzsfjvrgssfkfptchdocgtsfagdvtokppimtejhwcakrjmkrwprqshuzclzzwtavprjcpagumnzskrmgtmgjflohnhrzsfaihybozbkbngzntyteojjbkrrpzpbqseqckgmijudebwkxgcvcwblymwskncrrmmhhpkveeccchdthzrbbxfpgxlgrsusmiadfrzlyrhjuayiovqoicfnamvzabblyttbqnennrohvosumykxskricxlgqyefckkwjiadisuycjxguhcgl";
    }
}