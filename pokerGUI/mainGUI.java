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
import pokerhelp.Stats2Player;

public class MainGUI {

	protected Shell shell;
	private Text txtenterHandHere;
	private Text txtEnterTableCards;
	private Label lblWinningOdds;
	private Label lblOdds;
	private Label lblErrorLine1;
	private Label lblErrorLine2;
	private Label lblErrorLine3;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainGUI window = new MainGUI();
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
		shell.setSize(550, 300);
		shell.setText("Poker Helper");
		shell.setLayout(new GridLayout(2, false));
		
		Label lblEnterHandas = new Label(shell, SWT.NONE);
		lblEnterHandas.setText("Enter Hand and Community Cards Ex: ASAC (AS = Ace of Spades)");
		new Label(shell, SWT.NONE);
		
		txtenterHandHere = new Text(shell, SWT.BORDER);
		txtenterHandHere.setText("(Enter Hand Here)");
		txtenterHandHere.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnPress = new Button(shell, SWT.NONE);
		btnPress.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(e.getSource().equals(btnPress)) {
					if(!InputFormatting.isProperlyFormattedHand(txtenterHandHere.getText())) {
						lblErrorLine1.setText("Invalid Hand Format");
						System.out.println("Invalid Hand Format");
						lblErrorLine2.setText("Example Hand: AC0H");
						System.out.println("Example Hand: AC0H");
						lblErrorLine3.setText("Ace of Clubs 10 of Hearts");
						System.out.println("Ace of Clubs 10 of Hearts");
						return;
					}
					if(!InputFormatting.isProperlyFormattedTable(txtEnterTableCards.getText())) {
						System.out.println("Invalid Table Format");
						System.out.println("Example Table: AC4S7D6H9C");
						System.out.println("Ace of Clubs, 4 of Spades, 7 of Diamonds... etc");
						return;
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
		lblWinningOdds.setText("Winning Odds:");
		
		lblOdds = new Label(shell, SWT.NONE);
		lblOdds.setText("     ");
		
		lblErrorLine1 = new Label(shell, SWT.NONE);
		lblErrorLine1.setText("                                                               ");
		new Label(shell, SWT.NONE);
		
		lblErrorLine2 = new Label(shell, SWT.NONE);
		lblErrorLine2.setText("                                                               ");
		new Label(shell, SWT.NONE);
		
		lblErrorLine3 = new Label(shell, SWT.NONE);
		lblErrorLine3.setText("                                                               ");
		new Label(shell, SWT.NONE);
		

	}
	
	
	public void setOddsWithFlop() {
		String handText = txtenterHandHere.getText();
		String tableText = txtEnterTableCards.getText();
		Stats2Player stats = new Stats2Player();
		float winPercent = stats.winningOddsGivenFlop(new PokerHand(AllCards.getCard(handText.charAt(0), handText.charAt(1)),
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
		Stats2Player stats = new Stats2Player();
		float winPercent = stats.winningOddsGivenTurn(new PokerHand(AllCards.getCard(handText.charAt(0), handText.charAt(1)),
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
		Stats2Player stats = new Stats2Player();
		PokerTable table = new PokerTable(AllCards.getCard(tableText.charAt(0), tableText.charAt(1)),
										  AllCards.getCard(tableText.charAt(2), tableText.charAt(3)),
										  AllCards.getCard(tableText.charAt(4), tableText.charAt(5)),
										  AllCards.getCard(tableText.charAt(6), tableText.charAt(7)),
										  AllCards.getCard(tableText.charAt(8), tableText.charAt(9)));
		
		float winPercent = stats.winningOddsGivenTable(new PokerHand(AllCards.getCard(handText.charAt(0), handText.charAt(1)),
																				 AllCards.getCard(handText.charAt(2), handText.charAt(3))),
																		table);
		System.out.println(winPercent);
		lblOdds.setText("" + winPercent);
	}

}
