package fr.adaming.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.dao.ICommandeDao;
import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

@Repository("comService")
@Transactional
public class CommandeServiceImpl implements ICommandeService {

	@Autowired
	private ICommandeDao comDao;
	public void setComDao(ICommandeDao comDao) {
		this.comDao = comDao;
	}

	@Autowired
	private ILigneCommandeDao lcDao;
	public void setLcDao(ILigneCommandeDao lcDao) {
		this.lcDao = lcDao;
	}

	@Autowired
	private IProduitDao prDao;
	public void setPrDao(IProduitDao prDao) {
		this.prDao = prDao;
	}

	@Override
	public int ajouterCommande(Commande com) {

		return comDao.ajoutCommande(com);
	}

	@Override
	public int modifCommande(Commande com) {

		return comDao.modifCommande(com);
	}

	@Override
	public int supprCommande(Commande com) {
		// TODO Auto-generated method stub
		return comDao.supprCommande(com);
	}

	@Override
	public Commande getCommandeById(Commande com) {
		// TODO Auto-generated method stub
		return comDao.rechCommande(com);
	}

	@Override
	public List<Commande> getAllCommande() {
		// TODO Auto-generated method stub
		return comDao.getAllCommande();
	}

	@Override
	public void sendMail(Commande com) {

		// mon compte gmail (pour recevoir les messages)
		final String username = "bauchemin.c@gmail.com";
		final String password = "geol220891";

		// les propriétées
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// recuperer ma session
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			// creer l'objet message MimeMessage
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(username));

			// Set To: header field of the header.
			message
			.addRecipient(
					Message.
					RecipientType.
					TO, 
					new InternetAddress
					(com.
							getClient().
							getEmail()));

			// Set Subject: header du message
			message.setSubject("Mail facture");

			ByteArrayOutputStream outputStream = null;

			// Partie 1: Le texte
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText("Cher(e) Client(e)," + com.getClient().getNomClient() + "\n\n Merci de votre confiance!"
					+ "\n Vous trouverez ci-joint votre facture"
					+ "\n\n\n l'equipe Demba, Steven et Claire espère vous revoir bientôt sur notre site!");

			// ecrire le pdf dans outputStream
			outputStream = new ByteArrayOutputStream();
			writePdf(outputStream, com);
			byte[] bytes = outputStream.toByteArray();

			// construire le pdf
			DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
			MimeBodyPart pdfbp = new MimeBodyPart();
			pdfbp.setDataHandler(new DataHandler(dataSource));
			pdfbp.setFileName("facture.pdf");

			// On regroupe les deux dans le message
			MimeMultipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(pdfbp);
			message.setContent(mp);

			// on envoie le message
			Transport.send(message);

			System.out.println("Sent message successfully....");// verifier si
																// ca a reussi

		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}

	/**
	 * ecrire le pdf (using iText API)
	 */
	public void writePdf(ByteArrayOutputStream outputStream, Commande com) throws Exception {
		Document document = new Document();
		 PdfWriter.getInstance(document, outputStream);
	
		// ouvrir le document
		document.open();

		// données du document
		document.addTitle("Facture PDF");
		document.addSubject("Testing email PDF");
		document.addKeywords("iText, email");
		document.addAuthor("Steven, Demba et Claire");
		document.addCreator("Steven, Demba et Claire");

		/** récup de la liste des lignes commandes associées à la commande */
		List<LigneCommande> listeLc = lcDao.getListeLigneCommandeByComId(com);

		/** calcul du prix total */
		Double d = 0.0;
		for (LigneCommande lc : listeLc) {
			d += lc.getPrix();
		}
		
		// composition du pdf	
		
		 Image img = Image.getInstance(getClass().getClassLoader().getResource("logo-jardin-vert1.jpg"));
		 document.add(img);
	
		Paragraph paragraph = new Paragraph();
		paragraph.add(
				new Chunk("\n\n Cher(e) Mr(Mme) " + com.getClient().getNomClient() + "," ));
		document.add(paragraph);
		
		Paragraph paragraph1 = new Paragraph();
		paragraph1.add(
				new Chunk("Votre commande serat livrée sous 3 jours (ouvrables) à l'adresse suivante: \n" + com.getClient().getAdresse() + "," + "\n\n voici le recapitulatif de votre facture:\n\n"));
		document.add(paragraph1);
		
		//On créer un objet table dans lequel on intialise ça taille.
	      PdfPTable table = new PdfPTable(3);
		 //ajout des cellules entêtes
	      Font f = new Font(FontFamily.HELVETICA, 15, Font.BOLD, GrayColor.BLACK);
	        PdfPCell cell = new PdfPCell(new Phrase("Produit", f));
	        cell.setBackgroundColor(GrayColor.LIGHT_GRAY);
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell);
	        PdfPCell cell1 = new PdfPCell(new Phrase("Quantité", f));
	        cell1.setBackgroundColor(GrayColor.LIGHT_GRAY);
	        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell1);
	        PdfPCell cell2 = new PdfPCell(new Phrase("Prix", f));
	        cell2.setBackgroundColor(GrayColor.LIGHT_GRAY);
	        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell2);
	        
		/** Creation du recapitulatif de la commande*/
		for (LigneCommande lc1 : listeLc) {
		        
		      //on ajoute les cellules
		      table.addCell(prDao.rechProduit(lc1.getProduit()).getDesignation());
		      table.addCell( "x " +lc1.getQuantite());
		      table.addCell(prDao.rechProduit(lc1.getProduit()).getPrix()
					+ "€ x " + lc1.getQuantite());
		    
		     
			} 
		document.add(table);
		
		Paragraph paragraph2 = new Paragraph();
		paragraph2.add(new Chunk("______________________________________________________________________________"));
		document.add(paragraph2);

		Paragraph paragraph3 = new Paragraph();
		paragraph3.add(new Chunk("Prix total: " + d +" €"));
		document.add(paragraph3);

		Paragraph paragraph4 = new Paragraph();
		paragraph4.add(new Chunk(
				"\n\n\n l'equipe Demba, Steven et Claire espère vous revoir bientôt sur notre site!" + 
		"\n en cas de reclamation veillez nous contacter à l'adresse suivante:  nomane.boulmerdj@gmail.com"));
		document.add(paragraph4);
		
	
		// fermer le document
		document.close();
	}

}
