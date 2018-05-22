package com.school.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import com.school.users.User;
import com.school.users.UserLogin;

public class ViewProfilePanel 
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
	final JLabel lPhotoFile = new JLabel();
	ImageIcon icPhoto = new ImageIcon(
			new ImageIcon("Img/UserDP.png").getImage().
			getScaledInstance(DP_WIDTH, DP_HEIGHT, Image.SCALE_DEFAULT));
	final JTextField tfUserId = new JTextField("User ID");
	final JTextField tfFirstName = new JTextField(15);
	final JTextField tfLastName = new JTextField(15);
	final JTextArea tfAddress = new JTextArea();
	final JTextField tfPhone = new JTextField();
	final JTextField tfAltPhone = new JTextField();
	final JTextField tfEmail = new JTextField();
	final JPanel pPhoto = new JPanel();
	final JPanel pEditUserInfo = new JPanel();
	final JPanel pEditUserTextInfo = new JPanel();
	final JPanel pEditUserPhotoInfo = new JPanel();

	protected File idFile = new File("");
	protected File photoFile = new File("");

	public JPanel viewUserProfilePanel()
	{
		pEditUserInfo.setBackground(Color.WHITE);
		pEditUserTextInfo.setBackground(Color.WHITE);
		pEditUserPhotoInfo.setBackground(Color.WHITE);
		pEditUserInfo.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 60));

		tfFirstName.setEditable(false);
		tfLastName.setEditable(false);
		tfAddress.setEditable(false);
		tfPhone.setEditable(false);
		tfAltPhone.setEditable(false);
		tfEmail.setEditable(false);
		tfUserId.setEditable(false);

		tfAddress.setColumns(15);
		tfAddress.setWrapStyleWord(true);
		tfAddress.setLineWrap(true);
		tfAddress.setColumns(15);
		tfAddress.setRows(5);
		tfAddress.setBackground(Color.white);
		JScrollPane scPane = new JScrollPane(tfAddress);
		scPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		tfUserId.setText(UserLogin.getUserType().toString() + UserLogin.getId());
		ResultSet rs = LoginPanel.getUserLoginDb().viewProfile(UserLogin.getId());
		
		try
		{
			while(rs.next())
			{
				tfFirstName.setText(rs.getString("fname"));
				tfLastName.setText(rs.getString("lname"));
				tfAddress.setText(rs.getString("address"));
				tfPhone.setText(rs.getString("phone"));
				tfAltPhone.setText(rs.getString("alt_phone"));
				tfEmail.setText(rs.getString("email_id"));

				try
				{
					Blob blob=rs.getBlob("photo");
					icPhoto = new ImageIcon(new ImageIcon(blob.getBytes( 1L, (int) blob.length())).getImage().
							getScaledInstance(DP_WIDTH, DP_HEIGHT, Image.SCALE_DEFAULT));	
				}
				catch(Exception e)
				{
					System.out.println("Photo not uploaded");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

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
		pPhoto.setPreferredSize(new Dimension(200, 180));
		pPhoto.setBorder(BorderFactory.createEtchedBorder());

		lPhotoFile.setIcon(icPhoto);
		pPhoto.add(lPhotoFile);
		pEditUserPhotoInfo.add(pPhoto, gbc);

		pEditUserInfo.add(pEditUserTextInfo);
		pEditUserInfo.add(pEditUserPhotoInfo);
		return pEditUserInfo;
	}
}
