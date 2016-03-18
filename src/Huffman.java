/**
 * ******************************
 * Copyright 2015: smanna@cpp.edu
 * CS 241
 * PA3
 * ******************************
 * STUDENT: You need to write this class. You MUST 
 * implement the public and private methods as shown. 
 * Feel free to include your own private fields and 
 * methods as well if you find it necessary.
 * 
 * NOTE: You do not need to implement your own 
 * priority queue; you can use PriorityQueue 
 * from java API (already included for you in this class).
 * 
 * Also make sure to comment your code, otherwise 2 points 
 * will be deducted.
 */

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;

public class Huffman {

	public PriorityQueue<HuffmanNode> pQueue = new PriorityQueue<HuffmanNode>();
	public HashMap<Character, String> charToCode = new HashMap<Character, String>();
	public HashMap<String, Character> codeToChar = new HashMap<String, Character>();
	public HashMap<Character, Integer> hash = new HashMap<Character, Integer>();
	String text = "";
	String compressedText = "";

	// Constructor
	public Huffman(File file) throws FileNotFoundException {
		text = getText(file);
		char[] c = text.toCharArray();

		// add to hash map and keep track of frequency
		for (int i = 0; i < c.length; i++) {
			if (hash.get(c[i]) != null) { // already exists in hash map so
				int freq = hash.get(c[i]); // increase frequency
				hash.put(c[i], freq + 1);
			} else {
				hash.put(c[i], 1); // otherwise add new character to hash map
			}
		}

		for (Character key : hash.keySet()) { // create node for each character and add to queue
			HuffmanNode node = new HuffmanNode(key, hash.get(key));
			pQueue.add(node);
		}
				
		HuffmanNode n = buildTree(pQueue.size());
		buildTable(n);
	}

	// This method assumes that the tree and dictionary are already built
	public String compress() {
		for (int i = 0; i < text.length(); i++) {
			compressedText += charToCode.get(text.charAt(i));
		}
		return compressedText;
	}

	// This method assumes that the tree and dictionary are already built
	public String decompress() {
		String s = "";
		String result = "";
		for (int i = 0; i < compressedText.length(); i++) {
			s += compressedText.charAt(i); //
			if (codeToChar.containsKey(s)) { 
				result += codeToChar.get(s); // convert to character and add to string
				s = ""; // reset code
			}
		}
		return result;
	}

	// This method builds the table for the compression and decompression
	private void buildTable(HuffmanNode root) {
		postorder(root, "");
	}

	// This method builds the tree based on the frequency of every characters
	private HuffmanNode buildTree(int n) {
		for (int i = 1; i <= n - 1; i++) {
			HuffmanNode node = new HuffmanNode();
			node.left = pQueue.poll();
			node.right = pQueue.poll();
			node.frequency = node.left.frequency + node.right.frequency;
			pQueue.add(node);
		}
		return pQueue.poll(); // last node is root
	}

	public String getText(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		String str = "";
		while (sc.hasNextLine()) {
			str += sc.nextLine() + "\n";
		}
		sc.close();
		return str;
	}

	// This method is used to create the codes starting at root
	private void postorder(HuffmanNode n, String s) {
		if (n == null)
			return;
		if ((int) n.character < 0 || (int) n.character > 255)
			return;
		if (n.left == null && n.right == null) {
			charToCode.put(n.character, s); // build the conversion maps
			codeToChar.put(s, n.character);
		}
		postorder(n.left, s + "0");
		postorder(n.right, s + "1");
	}
	
	// Internal class
	// !!! DO NOT MAKE ANY CHANGES HERE!!!
	class HuffmanNode implements Comparable<HuffmanNode> {

		public char character;
		public int frequency;
		public HuffmanNode left, right;

		public HuffmanNode() {
		}

		public HuffmanNode(char character, int frequency) {
			this.character = character;
			this.frequency = frequency;
		}

		public String toString() {
			return character + " " + frequency;
		}

		public int compareTo(HuffmanNode that) {
			return this.frequency - that.frequency;
		}
	}
}
