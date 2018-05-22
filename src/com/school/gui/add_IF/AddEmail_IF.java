package com.school.gui.add_IF;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.school.DateLabelFormatter;
import com.school.GUIInterfaceUtilities;
import com.school.SystemUtils;
import com.school.database.ClassroomDb;
import com.school.database.DatabaseConnect;
import com.school.database.ExamDb;
import com.school.database.SubjectSelectDb;
import com.school.elements.Exam;
import com.school.elements.Subject;
import com.school.elements.Exam.ExamStatus;
import com.school.gui.SystemWindowSetup;
import com.school.gui.add_IF.AddExam_IF.AddExamInfoPanel;

public class AddEmail_IF 
{
	public final AddEmailPanel addEmailPanel = new AddEmailPanel();
	public final JPanel pEmail = new JPanel();
	final JInternalFrame addExam_IF = new JInternalFrame("Send Email", true, true, true, true);
	
	final CardLayout infoPanel = new CardLayout();
	final JLabel lNote = new JLabel("<html>Note: <br/>1) Go to 'https://www.google.com/settings/security/lesssecureapps' and enable option<br/>"
			+ "2) This software currently supports only one mail sending at a time so please dont enter multiple emails</html>");
	final JButton bSubmit = new JButton("Submit");
	final JButton bRefresh = new JButton("Refresh");
	final JPanel pButtons = new JPanel();
	int panelCount = 0;

	UtilDateModel model;
	JDatePanelImpl datePanel;
	JDatePickerImpl dpExam;	
	
	public JInternalFrame addEmail_IF()
	{
		final JPanel errorLogButtonsPanel = new JPanel();
		errorLogButtonsPanel.setLayout(new BorderLayout());
		pEmail.setLayout(infoPanel);
		pEmail.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		pEmail.add(addEmailPanel.createAddExamInfoPanel(), "ExamInfo");

		//setting up next and forward buttons
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
		pButtons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		bSubmit.setPreferredSize(bRefresh.getPreferredSize());
		pButtons.add(lNote);
		pButtons.add(bRefresh);
		pButtons.add(bSubmit);

		addExam_IF.setBounds(0, 0, SystemWindowSetup.IF_WIDTH, SystemWindowSetup.IF_HEIGHT);
		addExam_IF.setLayout(new BorderLayout());
		addExam_IF.add(pEmail, BorderLayout.CENTER);
		errorLogButtonsPanel.add(pButtons, BorderLayout.SOUTH);
		addExam_IF.add(errorLogButtonsPanel, BorderLayout.SOUTH);
		
		addExam_IF.setVisible(true);

		bRefresh.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				addEmailPanel.tfTo.setText("");
				addEmailPanel.tfMessage.setText("");
				addEmailPanel.tfSubject.setText("");
			}
		});
		bSubmit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				final String username = addEmailPanel.tfFrom.getText();
				final String password = String.valueOf(addEmailPanel.tfPassword.getPassword());

				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");

				Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(username));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(addEmailPanel.tfTo.getText()));
					message.setSubject(addEmailPanel.tfSubject.getText());
					message.setText(addEmailPanel.tfMessage.getText());

					Transport.send(message);

					JOptionPane.showMessageDialog(null, "Email sent successfully");

				} catch (MessagingException e1) {
					throw new RuntimeException(e1);
				}
			}
		});
		
		return addExam_IF;
	}
	
	protected class AddEmailPanel 
	{
		final JPanel pEmail = new JPanel();
		final JLabel lFrom = new JLabel("From Gmail ID: ");
		final JLabel lPassword = new JLabel("Gmail Password: ");
		final JLabel lTo = new JLabel("To: ");
		final JLabel lSubject = new JLabel("Subject: ");
		final JLabel lMessage = new JLabel("Message: ");
		final JTextField tfFrom = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JPasswordField tfPassword = new JPasswordField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JTextField tfTo = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JTextField tfSubject = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JTextArea tfMessage = new JTextArea();

		public JPanel createAddExamInfoPanel() 
		{
			tfMessage.setColumns(15);
			tfMessage.setWrapStyleWord(true);
			tfMessage.setLineWrap(true);
			tfMessage.setColumns(15);
			tfMessage.setRows(5);
			tfMessage.setBackground(Color.white);
			JScrollPane scPane = new JScrollPane(tfMessage);
			scPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
										
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pEmail.setLayout(gridBag);
		
			gbc.insets = new Insets(0, 20, 4, 5);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pEmail.add(lFrom, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pEmail.add(tfFrom, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pEmail.add(lPassword, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pEmail.add(tfPassword, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pEmail.add(lTo, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pEmail.add(tfTo, gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			pEmail.add(lSubject, gbc);
			gbc.gridx = 3;
			gbc.gridy = 3;
			pEmail.add(tfSubject, gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			pEmail.add(lMessage, gbc);
			gbc.gridx = 3;
			gbc.gridy = 4;
			pEmail.add(scPane, gbc);
			
			return pEmail;
		}
	}
}
