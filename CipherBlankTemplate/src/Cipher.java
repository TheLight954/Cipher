import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Cipher {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?-=:" + '\n' + '\r';
	private static final String SIMPLE_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	// Set this variable to the default alphabet you wish to use
	private static final String DEFAULT_ALPHABET = ALPHABET;
	public static Dictionary dictionary = Dictionary.buildDictionary("words.txt");
	public static String rotationCipherEncrypt(String plain, int shift, String alphabet) 
	{
		shift+=((-shift/alphabet.length())*alphabet.length());
		shift+=alphabet.length();
		String newText="";
		int cur;
		for(int i=0; i< plain.length(); i++)
		{
			cur=alphabet.indexOf(plain.substring(i,  i+1));
			cur+=shift;
			cur=cur%alphabet.length();
			newText+=alphabet.substring(cur, cur+1);
		}
		return newText;
	}
	public static String rotationCipherDecrypt(String cipher, int shift, String alphabet) 
	{
		shift+=((-shift/alphabet.length())*alphabet.length());
		shift=-shift;
		shift+=alphabet.length();
		String plain="";
		int cur;
		for(int i=0; i< plain.length(); i++)
		{
			cur=alphabet.indexOf(plain.substring(i,  i+1));
			cur+=shift;
			cur=cur%alphabet.length();
			plain+=alphabet.substring(cur, cur+1);
		}
		return plain;		
	}
	public static String vigenereCipherEncrypt(String plain, String password, String alphabet)
	{
		int cur, cur1;
		String ans="";
		for(int i=0; i<plain.length(); i++)
		{
			cur=alphabet.indexOf(plain.substring(i, i+1));
			cur1=alphabet.indexOf(password.substring(i%password.length(), i%password.length()+1));
			cur+=cur1;
			cur=cur%alphabet.length();
			ans+=alphabet.substring(cur,cur+1);
		}
		return ans;
	}
	public static String vigenereCipherDecrypt(String cipher, String password, String alphabet)
	{
		int cur, cur1;
		String ans="";
		for(int i=0; i<cipher.length(); i++)
		{
			cur=alphabet.indexOf(cipher.substring(i, i+1));
			cur1=alphabet.indexOf(password.substring(i%password.length(), i%password.length()+1));
			cur=cur+(alphabet.length()-cur1);
			cur=cur%alphabet.length();
			ans+=alphabet.substring(cur,cur+1);
		}
		return ans;		
	}
	public static String vigenereCipherCrackThreeLetter(String cipher, String alphabet)
	{
		LetterBag[] bag = new LetterBag[3];
		String code="";
		String ans="";
		String cur1;
		int cur;
		for(int i=0; i< 3; i++)
		{
			for(int j=i; j<cipher.length(); j+=3)
			{
				bag[i].add(cipher.substring(j, j+1));
			}
			cur=alphabet.indexOf(bag[i].getMostFrequent());
			cur=(cur+alphabet.length()-alphabet.indexOf(" "))%26;
			code+=alphabet.indexOf(cur);
		}
		for(int i=0; i< cipher.length(); i++)
		{
			cur=alphabet.indexOf(cipher.substring(i, i+1));
			cur+=alphabet.indexOf(code.substring(i%3, i%3+1));
			cur=cur%26;
			cur1=alphabet.substring(cur, cur+1);
			ans+=cur1;
		}
		return ans;
	}
	public static String vigenereCipherCrack(String cipher, int passwordLength)
	{
		LetterBag[] bag = new LetterBag[passwordLength];
		String Alphabet = "abcdefghijklmnopqrstuvwxyz";
		String code="";
		String ans="";
		String cur1;
		int cur;
		for(int i=0; i< passwordLength; i++)
		{
			for(int j=i; j< cipher.length(); j+=passwordLength)
			{
				bag[i].add(cipher.substring(j, j+1));
			}
			cur=Alphabet.indexOf(bag[i].getMostFrequent());
			cur=(cur+22)%26;
			code+=Alphabet.indexOf(cur);
		}
		for(int i=0; i< cipher.length(); i++)
		{
			cur=Alphabet.indexOf(cipher.substring(i, i+1));
			cur+=Alphabet.indexOf(code.substring(i%passwordLength, i%passwordLength+1));
			cur=cur%26;
			cur1=Alphabet.substring(cur, cur+1);
			ans+=cur1;
		}
		return ans;
	}
	public static String[] getWords(String text)
	{
		int counter=0;
		for(int i=0; i<text.length(); i++)
		{
			if(text.substring(i, i+1)==" ")
			{
				while(text.substring(i,i+1)==" ")
				{
					i++;
				}
				counter++;
			}
		}
		if(text.endsWith(" "))
		{
			counter--;
		}
		String[] Words = new String[counter];
		counter=0;
		for(int i=0; i<text.length(); i++)
		{
			if(text.substring(i, i+1)==" ")
			{
				while(text.substring(i,  i+1)==" ")
				{
					i++;
				}
				i--;
				counter++;
			}
			else
			{
				Words[counter]+=text.substring(i, i+1);
			}
		}
		return Words;
	}
	public static boolean isEnglish(String cipher)
	{
		String Alphabet="abcdefghijklmnopqrstuvwxyz";
		String newCipher="";
		String cur;
		for(int i=0; i<cipher.length(); i++)
		{
			cur=cipher.substring(i, i+1).toLowerCase();
			if(Alphabet.indexOf(cur)!=-1)
			{
				newCipher+=cipher.substring(i,i+1);
			}
		}
		String[] words=getWords(newCipher);
		int counter=0;
		for(int i=0; i<words.length; i++)
		{
			if(dictionary.isWord(words[i]))
			{
				counter++;
			}
		}
		if(counter*3>words.length)
		{
			return true;
		}
		return false;
	}
	public static String rotationCipherCrack(String cipher, String alphabet)
	{
		for(int i=0; i<alphabet.length(); i++)
		{
			if(isEnglish(rotationCipherDecrypt(cipher, i, alphabet)))
			{
				return rotationCipherDecrypt(cipher, i, alphabet);
			}
		}
		return "";
	}
	public static boolean isGood(String text)
	{
		String Alphabet="abcdefghijklmnopqrstuvwxyz";
		LetterBag bag = new LetterBag();
		int total=0;
		int[] frequencies = new int[] {817, 149, 278, 425, 1270, 223, 202, 609, 697, 15, 77, 403, 241, 675, 751, 193, 10, 599, 633, 906, 276, 98, 236, 15, 197, 7};
		String cur;
		int cur1, cur2;
		double error=0;
		for(int i=0; i<text.length(); i++)
		{
			cur=text.substring(i, i+1).toLowerCase();
			if(Alphabet.indexOf(cur)!=-1)
			{
				bag.add(cur);
				total+=1;
			}
		}
		for(int i=0; i<26; i++)
		{
			cur1=bag.getNumOccurances(text.substring(i,i+1));
			error+=((double)(cur1*10000/total)-(double)(frequencies[i]));
		}
		error/=100;
		if(error<10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static String vigenereCipherCrack(String cipher, String alphabet)
	{
		for(int i=0; i<cipher.length(); i++)
		{
			if(isGood(vigenereCipherCrack(cipher, i)))
			{
				return vigenereCipherCrack(cipher, i);
			}
		}
		return "";
	}
}