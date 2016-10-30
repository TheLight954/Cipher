import java.util.Arrays;
public class LetterBag {
	private final String alphabet = "abcdefghijklmnopqrstuwxyz";
	private int[] letterFrequencies;
	
	public LetterBag(){
		letterFrequencies = new int[26];   //26 letters
	}
	public void add (String letter) {
		String lower = letter.toLowerCase();
		int index = alphabet.indexOf(lower);
		letterFrequencies[index]++;
	}
	private int getIndexfForLetter(String lower) {
		return alphabet.indexOf(lower);
	}
	public String getMostFrequent()
	{
		String ans="";
		int curBest=0;
		for(int i=0; i< 26; i++)
		{
			if(letterFrequencies[i]>curBest)
			{
				curBest=letterFrequencies[i];
				ans=alphabet.substring(i, i+1);
			}
		}
		return ans;
	}
	public int getNumOccurances(String letter)
	{
		return letterFrequencies[alphabet.indexOf(letter)];
	}
	public int getNumUniqueWords()
	{
		int ans = 0;
		for(int i=0; i< 26; i++)
		{
			if(letterFrequencies[i]>0)
			{
				ans++;
			}
		}
		return ans;
	}
	public int getTotalWords()
	{
		int ans = 0;
		for(int i = 0; i <  26; i++)
		{
			ans+=letterFrequencies[i];
		}
		return ans;
	}
	public String getNMostFrequentStrings(int N)
	{
		int[] copyFrequencies = new int[26];
		for(int i=0; i< 26; i++)
		{
			copyFrequencies[i] = letterFrequencies[i];
		}
		Arrays.sort(copyFrequencies);
		for(int i=0; i< 26; i++)
		{
			if(letterFrequencies[i]==copyFrequencies[26-N])
			{
				return alphabet.substring(i, i+1);
			}
		}
		return "";
	}
}
