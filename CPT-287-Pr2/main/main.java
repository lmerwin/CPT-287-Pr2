import java.util.*;
import java.util.Stack;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) throws IOException {
		//Open input file, scanner, output file, and writer
		FileInputStream inputFile = new FileInputStream("E:/downloads/input.txt");
		Scanner scanner = new Scanner(inputFile);
		
		//Scan input file and call the functions to calculate expressions from input file
		while (scanner.hasNextLine()) {
			String infixExp = scanner.nextLine();
			//insert function for converting infix to postfix
			String postfix = infixToPostfix(infixExp);
			//insert function to evaluate postfix expression and print to screen
			System.out.println(evaluatePostfix(postfix));
		}
		scanner.close();
		inputFile.close();
		
		
	}


	
	
	
	

	
	public static int evaluatePostfix (String str) {
		
		//create a stack
		Stack<Integer> stack = new Stack<>();
		
		//scan all characters in str
		for(int i = 0; i < str.length(); i++) {
			char scan = str.charAt(i);
			
			if(scan == ' ') {
				continue;
			}
			
			//if scan is a number, push to stack
			else if(Character.isDigit(scan)) {
				int n = 0;
				
				//extract and store characters, this is for numbers with multiple digits (10, 11, etc)
				while(Character.isDigit(scan)) {
					n = n*10 + (int)(scan - '0');
					i++;
					scan = str.charAt(i);
				}
				i--;
				stack.push(n);
			}
			
			//else the character is an operator, pop 2 elements and apply operator
			else {
				int val1 = stack.pop();
				int val2 = stack.pop();
				char tempchar = 'a';
				char tempchar2 = 'b';
				
				
				//switch case to look for operators
				switch(scan) {
				case '+':
					stack.push(val2 + val1);
					break;
				
				case '-':
					stack.push(val2 +val1);
					break;
				
				case '/':
					if (val1 == 0) {
						System.out.println("Cannot divide by zero.");
						break;
					}
					else {
						stack.push(val2 / val1);
						break;
					}
				
				case '*':
					stack.push(val2 * val1);
					break;
					
				case '%':
					stack.push(val2 % val1);
					break;
					
				case '^':
					int stemp = (int)Math.pow(val2, val1);
					stack.push(stemp);
					break;
				
				case '<':
					//variable control
					tempchar = 'a';
					tempchar2 = 'b';

					//search for <=, else evaluate <
					while (!Character.isDigit(scan)) {
						tempchar = scan;
						i++;
						tempchar2 = str.charAt(i);
						break;
					}
					if ((tempchar == '<') && (tempchar2 == '=')){
						if (val2 <= val1) {
							stack.push(1);
							break;
						}
						else {
							stack.push(0);
							break;
						}
						
					}
					else {
						if (val2 < val1) {
							stack.push(1);
							break;
						}
						else {
							stack.push(0);
							break;
						}
					}
				
				case '>':
					//variable control
					tempchar = 'a';
					tempchar2 = 'b';
					
					//search for >=, else evaluate >
					while (!Character.isDigit(scan)) {
						tempchar = scan;
						i++;
						
						tempchar2 = str.charAt(i);
						break;
					}
					if ((tempchar == '>') && (tempchar2 == '=')){
						if (val2 >= val1) {
							stack.push(1);
							break;
						}
						else {
							stack.push(0);
							break;
						}
						
					}
					else {
						if (val2 > val1) {
							stack.push(1);
							break;
						}
						else {
							stack.push(0);
							break;
						}
					}
					
				
				case '=':
					//variable control
					tempchar = 'a';
					tempchar2 = 'b';

					//should only be ==, evaluate it
					while (!Character.isDigit(scan)) {
						tempchar = scan;
						i++;
						tempchar2 = str.charAt(i);
						break;
					}
					if ((tempchar == '=') && (tempchar2 == '=')){
						if (val2 == val1) {
							stack.push(1);
							break;
						}
						else {
							stack.push(0);
							break;
						}
						
					}
				
				case '!':
					//variable control
					tempchar = 'a';
					tempchar2 = 'b';
					
					//should only be !=, evaluate it
					while (!Character.isDigit(scan)) {
						tempchar = scan;
						i++;
						tempchar2 = str.charAt(i);
						break;
					}
					if ((tempchar == '!') && (tempchar2 == '=')){
						if (val2 != val1) {
							stack.push(1);
							break;
						}
						else {
							stack.push(0);
							break;
						}
						
					}
					
				case '&':
					//variable control
					tempchar = 'a';
					tempchar2 = 'b';

					//should only be &&, evaluate it
					while (!Character.isDigit(scan)) {
						tempchar = scan;
						i++;
						tempchar2 = str.charAt(i);
						break;
					}
					if ((tempchar == '&') && (tempchar2 == '&')){
						if (val2 == val1) {
							stack.push(1);
							break;
						}
						else {
							stack.push(0);
							break;
						}
						
					}
					
				case '|':
					//variable control
					tempchar = 'a';
					tempchar2 = 'b';
					
					//should only be ||, evaluate it
					while (!Character.isDigit(scan)) {
						tempchar = scan;
						i++;
						tempchar2 = str.charAt(i);
						break;
					}
					if ((tempchar == '|') && (tempchar2 == '|')){
						if (val2 >= val1) {
							stack.push(1);
							break;
						}
						else {
							stack.push(0);
							break;
						}
						
					}

				}
			}
		
		}
		
		return stack.pop();
	}
	
	
	public static String infixToPostfix(String infixExp) {
		// Initialize an empty stack
		Stack<String> stk = new Stack<>();
		// Initialize postfix string
		StringBuilder postfix = new StringBuilder();
		Scanner scanner = new Scanner(infixExp);
		while (scanner.hasNext()) {
			String token = scanner.next();
			if (Character.isDigit(token.charAt(0))) { postfix.append(token).append(' '); }
			// opening parenthesis
			else if (token.equals("(")) { stk.push(token); }
			// closing parenthesis
			else if (token.equals(")")) {
				while (!stk.peek().equals("(")) { postfix.append(stk.pop()).append(' '); }
				stk.pop();
			}
			// operator
			else {
				while (!stk.isEmpty() && !stk.peek().equals("(") && precedence(token) <= precedence(stk.peek())) {
                    postfix.append(stk.pop()).append(' ');
				}
				 // Push the current operator onto the stack.
                stk.push(token);
			}
		}
		// Pop the remaining operators from the stack and append them to the postfix expression string.
        while (!stk.isEmpty()) { postfix.append(stk.pop()).append(' '); }
        scanner.close();
        return postfix.toString();
	}
	
	
	
	/** Returns the precedence of an operator.
	* @param operator: the operator to find its precedence
	* @return: the precedence of the operator
	*/
public static int precedence(String operator) {
	//Set each operators precedence
	//The higher the precedence the higher int returned
	if (operator.equals("^")) { return 7; }
	if (operator.equals("*") || operator.equals("/") || operator.equals("%")) { return 6; }
	if (operator.equals("+") || operator.equals("-")) { return 5; }
	if (operator.equals("<") || operator.equals("<=") || operator.equals(">") || operator.equals(">=")) { return 4; }
	if (operator.equals("==") || operator.equals("!=")) { return 3; }
	if (operator.equals("&&")) { return 2; }
	if (operator.equals("||")) { return 1; }
	// Throw exception for non supported operators
	throw new IllegalArgumentException(String.format("Operator %s is not a valid operator.", operator));
}


}