package com.school.gui.admin.edit_IF;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.school.GUIInterfaceUtilities;
import com.school.SystemUtils;
import com.school.gui.InputValidators.JCompInputVerifier;
import com.school.gui.InputValidators.PhotoFilter;
import com.school.users.Student;
import com.school.users.User;

public class EditUserInfoPanel 
{
	private static final int DP_WIDTH = 200;
	private static final int DP_HEIGHT = 175;
	
	protected static int photoFileValue = 0;
	protected static int idFileValue = 0;
	public static User user;
	
	final JLabel lUserId = new JLabel("User ID: ");
	final JLabel lFirstName = new JLabel("First Name: ");
	final JLabel lLastName = new JLabel("Last Name: ");
	final JLabel lAddress = new JLabel("Address: ");
	final JLabel lPhone = new JLabel("Phone: ");
	final JLabel lAltPhone = new JLabel("Alternate Phone: ");
	final JLabel lEmail = new JLabel("Email: ");
	final JLabel lPhoto = new JLabel("Photo: ");
	final JLabel lIDproof = new JLabel("ID Proof: ");
	final JLabel lPhotoFile = new JLabel();
	final ImageIcon icPhoto = new ImageIcon(
			new ImageIcon("Img/UserDP.png").getImage().
			getScaledInstance(DP_WIDTH, DP_HEIGHT, Image.SCALE_DEFAULT));
	final JTextField tfUserId = new JTextField("User ID");
	final JTextField tfFirstName = new JTextField(15);
	final JTextField tfLastName = new JTextField(15);
	final JTextArea tfAddress = new JTextArea();
	final JTextField tfPhone = new JTextField();
	final JTextField tfAltPhone = new JTextField();
	final JTextField tfEmail = new JTextField();
	final JTextField tfIDProof = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE + 3);
	final JButton bBrowse = new JButton("Browse");
	final JPanel pPhoto = new JPanel();
	final JPanel pEditUserInfo = new JPanel();
	final JPanel pEditUserTextInfo = new JPanel();
	final JPanel pEditUserPhotoInfo = new JPanel();
	final TitledBorder border = new TitledBorder("User Information");
	
	protected File idFile = new File("");
	protected File photoFile = new File("");
	
	public JPanel createEditUserInfoPanel()
	{
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitlePosition(TitledBorder.CENTER);
		pEditUserInfo.setBorder(border);
		pEditUserInfo.setBackground(Color.WHITE);
		pEditUserTextInfo.setBackground(Color.WHITE);
		pEditUserPhotoInfo.setBackground(Color.WHITE);
		pEditUserInfo.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 60));
		
		//setting names for fields to retrieve them for input verifier
		tfFirstName.setName("First Name");
		tfLastName.setName("Last Name");
		tfPhone.setName("Phone");
		tfAltPhone.setName("Alternate Phone");
		tfEmail.setName("Email");
		tfAddress.setName("Address");
		tfIDProof.setName("ID Proof");		
		tfUserId.setEditable(false);
		tfIDProof.setEditable(false);
		
		tfAddress.setColumns(15);
		tfAddress.setWrapStyleWord(true);
		tfAddress.setLineWrap(true);
		tfAddress.setColumns(15);
		tfAddress.setRows(5);
		tfAddress.setBackground(Color.white);
		JScrollPane scPane = new JScrollPane(tfAddress);
		scPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		tfUserId.setText(user.getUserType().toString() + user.getId());
		tfFirstName.setText(user.getfName());
		tfLastName.setText(user.getlName());
		tfPhone.setText(String.valueOf(user.getPhone()));
		tfAltPhone.setText(String.valueOf(user.getAltPhone()));
		tfEmail.setText(user.getEmailid());
		tfAddress.setText(user.getAddress());	
		
		bBrowse.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						JFileChooser file = new JFileChooser();
						file.setAcceptAllFileFilterUsed(false);
						FileFilter documentFilter = new FileNameExtensionFilter(
								"Images and documents only", SystemUtils.DOC_FILE_FORMATS);
						file.setFileFilter(documentFilter);
						int idFileValue = file.showOpenDialog(null);
						if(idFileValue == JFileChooser.APPROVE_OPTION)
						{
							EditUserInfoPanel.idFileValue = idFileValue;
							idFile = file.getSelectedFile();
							tfIDProof.setText(idFile.toString());
						}
					}
				});
		pPhoto.addMouseListener(new MouseListener()
				{
					@Override
					public void mouseClicked(MouseEvent arg0) 
					{
						JFileChooser file = new JFileChooser();
						file.setAcceptAllFileFilterUsed(false);
						file.setFileFilter(new PhotoFilter());
						int photoFileValue = file.showOpenDialog(null);
						if(photoFileValue == JFileChooser.APPROVE_OPTION)
						{
							EditUserInfoPanel.photoFileValue = photoFileValue;
							photoFile = file.getSelectedFile();
							lPhotoFile.setIcon(new ImageIcon(
									new ImageIcon(photoFile.toString()).getImage().
									getScaledInstance(DP_WIDTH, DP_HEIGHT, Image.SCALE_DEFAULT)));
						}
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {	}
					@Override
					public void mouseExited(MouseEvent arg0) {}
					@Override
					public void mousePressed(MouseEvent arg0) {}
					@Override
					public void mouseReleased(MouseEvent arg0) {}
				});
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run() 
			{
				tfFirstName.requestFocusInWindow();
			}
		});
		
		final GridBagLayout gridBag = new GridBagLayout();
		final GridBagConstraints gbc = new GridBagConstraints();
		pEditUserTextInfo.setLayout(gridBag);
	
		gbc.insets = new Insets(0, 20, 4, 20);	//for padding in gridbag layout
		gbc.fill = GridBagConstraints.HORIZONTAL;		
		gbc.gridx = 0;
		gbc.gridy = 0;
		pEditUserTextInfo.add(lUserId, gbc);
		gbc.gridx = 3;
		gbc.gridy = 0;
		pEditUserTextInfo.add(tfUserId, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		pEditUserTextInfo.add(lFirstName, gbc);
		gbc.gridx = 3;
		gbc.gridy = 1;
		pEditUserTextInfo.add(tfFirstName, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		pEditUserTextInfo.add(lLastName, gbc);
		gbc.gridx = 3;
		gbc.gridy = 2;
		pEditUserTextInfo.add(tfLastName, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		pEditUserTextInfo.add(lAddress, gbc);
		gbc.gridx = 3;
		gbc.gridy = 3;
		pEditUserTextInfo.add(scPane, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		pEditUserTextInfo.add(lPhone, gbc);
		gbc.gridx = 3;
		gbc.gridy = 4;
		pEditUserTextInfo.add(tfPhone, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		pEditUserTextInfo.add(lAltPhone, gbc);
		gbc.gridx = 3;
		gbc.gridy = 5;
		pEditUserTextInfo.add(tfAltPhone, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		pEditUserTextInfo.add(lEmail, gbc);
		gbc.gridx = 3;
		gbc.gridy = 6;
		pEditUserTextInfo.add(tfEmail, gbc);
	
		pEditUserPhotoInfo.setLayout(gridBag);
	
		gbc.insets = new Insets(0, 20, 4, 20);	//for padding in gridbag layout
		gbc.fill = GridBagConstraints.HORIZONTAL;		
		gbc.gridx = 18;
		gbc.gridy = 0;
		pEditUserPhotoInfo.add(lPhoto, gbc);
		gbc.gridx = 18;
		gbc.gridy = 1;
		pPhoto.setLayout(new GridLayout(0, 1));
		pPhoto.setPreferredSize(new Dimension(0, 180));
		pPhoto.setBorder(BorderFactory.createEtchedBorder());
		
		lPhotoFile.setIcon(icPhoto);
		pPhoto.add(lPhotoFile);
		pEditUserPhotoInfo.add(pPhoto, gbc);
		gbc.gridx = 18;
		gbc.gridy = 5;
		pEditUserPhotoInfo.add(lIDproof, gbc);
		gbc.gridx = 18;
		gbc.gridy = 6;
		gbc.insets = new Insets(0, 20, 4, 5);	//for padding in gridbag layout
		pEditUserPhotoInfo.add(tfIDProof, gbc);
		bBrowse.setPreferredSize(new Dimension(80, 25));
		gbc.gridx = 19;
		gbc.gridy = 6;
		gbc.insets = new Insets(0, 0, 0, 0);	//for padding in gridbag layout
		pEditUserPhotoInfo.add(bBrowse, gbc);
		
		pEditUserInfo.add(pEditUserTextInfo);
		pEditUserInfo.add(pEditUserPhotoInfo);
		return pEditUserInfo;
	}
	
	public class EditUserLoginInfoPanel
	{
		final JLabel lUserID = new JLabel("User ID: ");
		final JLabel lPassword = new JLabel("Password: ");
		final JLabel lConfirmPassword = new JLabel("Confirm Password: ");
		final JTextField tfUserID = new JTextField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JPasswordField tfPassword = new JPasswordField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JPasswordField tfConfirmPassword = new JPasswordField(GUIInterfaceUtilities.TEXTFIELD_SIZE);
		final JPanel pUserLoginInfo = new JPanel();
		final TitledBorder border = new TitledBorder("User Login Information");
		
		public boolean confirmPassword(char[] password)
		{
			if(tfConfirmPassword.getPassword().equals(tfPassword.getPassword()))
			{
				EditStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(Color.green),
						BorderFactory.createEmptyBorder(0, 10, 0, 0)));
				EditStudent_IF.error.setText("");
				
				return true;
			}
			else
			{
				EditStudent_IF.pInputError.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(Color.red),
						BorderFactory.createEmptyBorder(0, 10, 0, 0)));
				EditStudent_IF.error.setText("Passwords do not match");
				
				return false;
			}
		}
		
		public JPanel createUserLoginInfoPanel()
		{
			pUserLoginInfo.setBackground(Color.WHITE);
			border.setTitleJustification(TitledBorder.LEFT);
			border.setTitlePosition(TitledBorder.CENTER);
			pUserLoginInfo.setBorder(border);
			pUserLoginInfo.setBackground(Color.WHITE);
			
			tfUserID.setText(user.getUserType().toString() + user.getId());
			tfPassword.setText(user.getPassword());
			tfConfirmPassword.setText(user.getPassword());
			
			tfUserID.setEditable(false);
			tfPassword.setName("Password");
			tfPassword.setInputVerifier(new JCompInputVerifier());
			
			//Login Panel that will contain above components
			final GridBagLayout gridBag = new GridBagLayout();
			final GridBagConstraints gbc = new GridBagConstraints();
			pUserLoginInfo.setLayout(gridBag);
			
			tfConfirmPassword.getDocument().addDocumentListener(new DocumentListener()
					{
						@Override
						public void changedUpdate(DocumentEvent arg0) 
						{
							confirmPassword(tfConfirmPassword.getPassword());
						}

						@Override
						public void insertUpdate(DocumentEvent arg0) 
						{
							confirmPassword(tfConfirmPassword.getPassword());	
						}

						@Override
						public void removeUpdate(DocumentEvent arg0) 
						{
							confirmPassword(tfConfirmPassword.getPassword());
						}
					});
		
			gbc.insets = new Insets(0, 4, 4, 4);	//for padding in gridbag layout
			gbc.fill = GridBagConstraints.HORIZONTAL;		
			gbc.gridx = 0;
			gbc.gridy = 0;
			pUserLoginInfo.add(lUserID, gbc);
			gbc.gridx = 3;
			gbc.gridy = 0;
			pUserLoginInfo.add(tfUserID, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			pUserLoginInfo.add(lPassword, gbc);
			gbc.gridx = 3;
			gbc.gridy = 1;
			pUserLoginInfo.add(tfPassword, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			pUserLoginInfo.add(lConfirmPassword, gbc);
			gbc.gridx = 3;
			gbc.gridy = 2;
			pUserLoginInfo.add(tfConfirmPassword, gbc);
			
			return pUserLoginInfo;
		}
	}
}

