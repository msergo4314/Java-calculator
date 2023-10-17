package pckg;
/*
 * Author: Martin Sergo (with some minor assistance from chat gpt...
 * 
 * It's a calculator. The only major flaw is that it will crash if the user input string cannot actually be solved
 * Otherwise, it works very well
 * numbers with an exp component can be displayed but CANNOT be operated on
 * 
 */
import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame{
	
	private Font font = new Font("Times new roman", Font.BOLD, 26); // set the font
	
	private String operationString = "";
	private String ans;
	
	JTextField myTextField = new JTextField();
	JTextField miniField = new JTextField();
	
	public Calculator () {
		this.setTitle("My simple Calculator");
		this.setSize(510, 500);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(createTopPanel(), BorderLayout.NORTH);
		this.add(createBottomPanel(), BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private JPanel createTopPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setBackground(Color.black);
		panel.setPreferredSize(new Dimension(this.getWidth(), 70));
		panel.add(CreateTextField());
		panel.add(CreateMiniField());
		panel.setVisible(true);
		return panel;
	}

	private JTextField CreateMiniField() {
		
		miniField.setPreferredSize(new Dimension(160, 70));
		miniField.setFont(font);
		miniField.setBorder(BorderFactory.createEtchedBorder());
		miniField.setFocusable(false);
		miniField.setVisible(true);
		miniField.setForeground(Color.red);
		miniField.setBackground(Color.black);
		miniField.setEditable(false);
		miniField.setHorizontalAlignment(JTextField.RIGHT);
		miniField.setText(operationString);
		return miniField;
	}

	private JTextField CreateTextField() {
		
		myTextField.setPreferredSize(new Dimension(330, 70));
		myTextField.setFont(font);
		myTextField.setBorder(BorderFactory.createEtchedBorder());
		myTextField.setFocusable(false);
		myTextField.setVisible(true);
		myTextField.setForeground(Color.red);
		myTextField.setBackground(Color.black);
		//myTextField.setCaretColor(Color.red);
		myTextField.setEditable(false);
		myTextField.setText(operationString);
		myTextField.setHorizontalAlignment(JTextField.LEFT);
		
		return myTextField;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setLayout(new BorderLayout());
		panel.setOpaque(true);
		panel.setVisible(true);
		
		panel.add(CreateRightPanel(), BorderLayout.EAST);
		panel.add(CreateGridPanel(), BorderLayout.CENTER);
		
		return panel;
	}

	private JPanel CreateGridPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setLayout(new GridLayout(4,3,5,5));
		panel.add(createButton("7"));
		panel.add(createButton("8"));
		panel.add(createButton("9"));
		panel.add(createButton("4"));
		panel.add(createButton("5"));
		panel.add(createButton("6"));
		panel.add(createButton("1"));
		panel.add(createButton("2"));
		panel.add(createButton("3"));
		panel.add(createButton("0"));
		panel.add(createButton("."));
		panel.add(CreateButtonSpecial(2));
		panel.setVisible(true);
		return panel;
	}

	private JPanel CreateRightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setOpaque(true); // Set opaque to true
        //panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setVisible(true);
        panel.setPreferredSize(new Dimension(165, 200));

        panel.add(CreateButtonSpecial(0), BorderLayout.NORTH);
        panel.add(createMiniGrid(), BorderLayout.CENTER);
        return panel;
	}
	
	private JPanel createMiniGrid() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setOpaque(true);
		panel.setLayout(new GridLayout(4,2,5,5));
		panel.add(createButton("*"));
		panel.add(createButton("/"));
		panel.add(createButton("+"));
		panel.add(createButton("-"));
		panel.add(createButton("("));
		panel.add(createButton(")"));
		panel.add(createButton("^"));
		panel.add(CreateButtonSpecial(1));
		panel.setVisible(true);
		return panel;
	}

	private JButton CreateButtonSpecial(int i) {
		JButton button = new JButton();
		
		switch(i) {
		case 0:
			return CreateClearButton();
		case 1:
			return createEqualsButton();
		default:
			return createDeleteButton();	
		}
	}

	private JButton CreateClearButton() {
		JButton button = new JButton("Clear");
		button.setFont(font);
		button.setFocusable(false);
		button.setBackground(Color.LIGHT_GRAY);
		button.setPreferredSize(new Dimension(155, 50));
		button.setBorder(BorderFactory.createEtchedBorder());
		button.addActionListener(e -> {
			operationString = ""; // reset the string
			myTextField.setText(operationString);
			miniField.setText("");
		});
		
		return button;
	}
	
	private JButton createEqualsButton() {
		JButton button = new JButton("=");
		button.setFont(font);
		button.setFocusable(false);
		button.setPreferredSize(new Dimension(50, 60));
		button.setBackground(Color.LIGHT_GRAY);
		button.setBorder(BorderFactory.createEtchedBorder());
		button.addActionListener(e -> {
			ans = Double.toString(MathExpressionEvaluator.evaluateExpression(operationString));
			System.out.println("answer is: " + ans + "(length: "  + ans.length() +")");
			String tailString = "";
			for(int i = 0; i < ans.length(); i++) {
				if(ans.charAt(i) == 'E') { // if has Exp component, display that
					tailString = ans.substring(i, ans.length());
					ans = ans.substring(0,i);
				} 
			}
			if(ans.length() > 8) {
				ans = ans.substring(0,8);
			}
			myTextField.setText(operationString + " =");
			if (ans.equals("Infinity")) {
				ans = ("ERROR");
			}
			miniField.setText(ans + tailString);
			if (!ans.equals("ERROR")) {
				operationString = ans + tailString;
			} else {
				operationString = "";
			}
		});
		
		return button;
	}
	
	private JButton createDeleteButton() {
		JButton button = new JButton("Delete");
		button.setFont(font);
		button.setFocusable(false);
		button.setBackground(Color.LIGHT_GRAY);
		button.setBorder(BorderFactory.createEtchedBorder());
		button.addActionListener(e -> {
			if (!operationString.isBlank()) {
				StringBuilder sb = new StringBuilder(operationString);
				sb.deleteCharAt(sb.length() - 1);
				operationString = sb.toString();
				myTextField.setText(operationString);
			}
		});
		
		return button;
	}

	public JButton createButton(String name) {
		JButton button = new JButton(name);
		button.setFont(font);
		button.setFocusable(false);
		button.setBackground(Color.LIGHT_GRAY);
		button.setBorder(BorderFactory.createEtchedBorder());
		button.setPreferredSize(new Dimension(75, 77));
		button.addActionListener(e -> {
			operationString = operationString.concat(name); // add to the string
			myTextField.setText(operationString);
		});
		
		return button;
	}
 }

