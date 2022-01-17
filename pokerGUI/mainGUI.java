package pokerGUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pokerParts.AllCards;
import pokerParts.PokerHand;
import pokerParts.PokerTable;
import pokerhelp.PokerStats2Players;

public class mainGUI {

	protected Shell shell;
	private Text txtenterHandHere;
	private Text txtEnterTableCards;
	private Label lblWinningOdds;
	private Label lblOdds;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			mainGUI window = new mainGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Poker Helper");
		shell.setLayout(new GridLayout(2, false));
		
		Label lblEnterHandas = new Label(shell, SWT.NONE);
		lblEnterHandas.setText("Enter Hand and Community Cards (AS = Ace of Spades)");
		new Label(shell, SWT.NONE);
		
		txtenterHandHere = new Text(shell, SWT.BORDER);
		txtenterHandHere.setText("(Enter Hand Here)");
		txtenterHandHere.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnPress = new Button(shell, SWT.NONE);
		btnPress.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(e.getSource().equals(btnPress)) {
					if(txtenterHandHere.getText().length() != 4) {
						System.out.println("Invalid Hand Format");
						System.out.println("Example Hand \"ACAS\" (Ace of Clubs Ace of Spades)");
					}
				else {
					switch (txtEnterTableCards.getText().length()) {
						case 6 : {
							setOddsWithFlop();
							break;
						}
						case 8 : {
							setOddsWithTurn();
							break;
						}
						case 10 : {
							setOddsWithRiver();
							break;
						}
						default : {
							System.out.println("Invalid Table Format");
							System.out.println("Example Hand \"ACASAD\"");
						}
					}
				}
			}
		}});
		btnPress.setText("Press");
		
		txtEnterTableCards = new Text(shell, SWT.BORDER);
		txtEnterTableCards.setText("(Enter Community Cards)");
		txtEnterTableCards.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);
		
		lblWinningOdds = new Label(shell, SWT.NONE);
		lblWinningOdds.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblWinningOdds.setText("Winning Odds");
		
		lblOdds = new Label(shell, SWT.NONE);
		lblOdds.setText("Odds");
		

	}
	
	
	public void setOddsWithFlop() {
		String handText = txtenterHandHere.getText();
		String tableText = txtEnterTableCards.getText();
		
		float winPercent = PokerStats2Players.winningOddsGivenFlop(new PokerHand(AllCards.getCard(handText.charAt(0), handText.charAt(1)),
																				 AllCards.getCard(handText.charAt(2), handText.charAt(3))),
																	AllCards.getCard(tableText.charAt(0), tableText.charAt(1)), 
																	AllCards.getCard(tableText.charAt(2), tableText.charAt(3)), 
																	AllCards.getCard(tableText.charAt(4), tableText.charAt(5)));
		System.out.println(winPercent);
		lblOdds.setText("" + winPercent);
	}
	
	public void setOddsWithTurn() {
		String handText = txtenterHandHere.getText();
		String tableText = txtEnterTableCards.getText();
		
		float winPercent = PokerStats2Players.winningOddsGivenTurn(new PokerHand(AllCards.getCard(handText.charAt(0), handText.charAt(1)),
																				 AllCards.getCard(handText.charAt(2), handText.charAt(3))),
																	AllCards.getCard(tableText.charAt(0), tableText.charAt(1)), 
																	AllCards.getCard(tableText.charAt(2), tableText.charAt(3)), 
																	AllCards.getCard(tableText.charAt(4), tableText.charAt(5)),
																	AllCards.getCard(tableText.charAt(6), tableText.charAt(7)));
		System.out.println(winPercent);
		lblOdds.setText("" + winPercent);
	}
	
	public void setOddsWithRiver() {
		String handText = txtenterHandHere.getText();
		String tableText = txtEnterTableCards.getText();
		PokerTable table = new PokerTable(AllCards.getCard(tableText.charAt(0), tableText.charAt(1)),
										  AllCards.getCard(tableText.charAt(2), tableText.charAt(3)),
										  AllCards.getCard(tableText.charAt(4), tableText.charAt(5)),
										  AllCards.getCard(tableText.charAt(6), tableText.charAt(7)),
										  AllCards.getCard(tableText.charAt(8), tableText.charAt(9)));
		
		float winPercent = PokerStats2Players.winningOddsGivenTable(new PokerHand(AllCards.getCard(handText.charAt(0), handText.charAt(1)),
																				 AllCards.getCard(handText.charAt(2), handText.charAt(3))),
																		table);
		System.out.println(winPercent);
		lblOdds.setText("" + winPercent);
	}

}
