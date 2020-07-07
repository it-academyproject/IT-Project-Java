package com.it_academyproject.services;

import java.util.*;

import javax.mail.internet.MimeMessage;

import com.it_academyproject.controllers.DTOs.statsDTOs.DTOStudentsExerciseNotTurnedInI;
import com.it_academyproject.domains.*;
import com.it_academyproject.repositories.*;
import com.it_academyproject.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	public JavaMailSender javaMailSender;

	@Autowired
	public CourseRepository courseRepository;

	@Autowired
	public AbsenceRepository absenceRepository;

	@Autowired
	public EmailsRepository emailsRepository;

	@Autowired
	public UserExerciseRepository userExerciseRepository;

	@Autowired
	public MyAppUserRepository userRepository;

	///////////////////////////////////////////////    8 ABSENCE NOTIFICATION    ///////////////////////////////////////////////////////////////

	public void notificationEmailAbsence() {


		List<Absence> absence = new ArrayList<>();
		absenceRepository.findAll().forEach(absence::add);

		for(int i = 0; i < absence.size(); i++) {  
			Integer counter = 0; 
			for(int j = i + 1; j < absence.size(); j++) {  
				if(absence.get(i).getUserStudent().getId().equals(absence.get(j).getUserStudent().getId())) {  
					counter++;  
				}
			}
			if(counter==8) {    	
				List<Emails> emailsSent = new ArrayList<>();
				emailsRepository.findByUserStudent(absence.get(i).getUserStudent()).forEach(emailsSent::add);


				boolean hasBeenNotified = false;

				for (int j=0; j<emailsSent.size(); j++) {
					if (emailsSent.get(j).isSent() == true && emailsSent.get(j).getEmailType().getId() == 1) {

						hasBeenNotified = true;
					}
				}

				if (hasBeenNotified == false) {

					Emails emailsListNotification = new Emails("ABSENCES");
					emailsListNotification.setUserStudent(absence.get(i).getUserStudent());
					emailsListNotification.setSent(false);								
					emailsRepository.save(emailsListNotification);



					String email = absence.get(i).getUserStudent().getEmail();        
					String name = absence.get(i).getUserStudent().getFirstName();
					String messageBody = "                                            I hope this e-mail finds you well. This message is to inform you, that you have "+counter+" absences in"+
							"                                            the IT Academy course.";
					if (email!=null && name!=null) {
						try {
							emailsListNotification.setSent(true);	
							emailsRepository.save(emailsListNotification);
							emailNotification(email,name,messageBody);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}


			}   		
		}	


	}


	///////////////////////////////////////////////    15 DAYS OR 30 DAY LEFT NOTIFICATION    ///////////////////////////////////////////////////////////////

	public void notificationEmailDaysLeft() {


		List<Course> finishInXDays = new ArrayList<>();
		courseRepository.findAll().forEach(finishInXDays::add);

		Date currentDate = Calendar.getInstance().getTime();

		for(int i=0;i<finishInXDays.size();i++) {
			if (finishInXDays.get(i).getEndDate() != null) {
				Long days = (Long) ((finishInXDays.get(i).getEndDate().getTime()-currentDate.getTime())/86400000); 
				int daysInt = (int) days.longValue();
				if (daysInt==15) {
					Emails emailsListNotification = new Emails("DAYSLEFT15");                                  
					emailsListNotification.setUserStudent(finishInXDays.get(i).getUserStudent()); 
					emailsListNotification.setSent(false);	
					emailsRepository.save(emailsListNotification); 
					String email = finishInXDays.get(i).getUserStudent().getEmail();   
					String name = finishInXDays.get(i).getUserStudent().getFirstName();
					String messageBody = "                                            I hope this e-mail finds you well. This message is to inform you, that you are about to end the IT Academy"+
							"                                            course in less than " + daysInt +" days.";
					if (email!=null && name!=null) {
						try {
							emailsListNotification.setSent(true);
							emailsRepository.save(emailsListNotification);
							emailNotification(email,name,messageBody);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}  
				if (daysInt==30) {
					Emails emailsListNotification = new Emails("DAYSLEFT30");                                 
					emailsListNotification.setUserStudent(finishInXDays.get(i).getUserStudent()); 
					emailsListNotification.setSent(false);	
					emailsRepository.save(emailsListNotification); 
					String email = finishInXDays.get(i).getUserStudent().getEmail();    
					String name = finishInXDays.get(i).getUserStudent().getFirstName();
					String messageBody = "                                            I hope this e-mail finds you well. This message is to inform you, that you are about to end the IT Academy"+
							"                                            course in less than " + daysInt +" days.";
					if (email!=null && name!=null) {
						try {
							emailsListNotification.setSent(true);
							emailsRepository.save(emailsListNotification);
							emailNotification(email,name,messageBody);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void notificationEmailExercisesNotTurnedIn() {

		Integer notTurnedInDays = ApplicationConfig.STUDENT_EXERCISE_NOT_TURNED_IN_DAYS;
		List<Integer> exerciseStatusExclude = ApplicationConfig.STUDENT_EXERCISE_STATUS_EXCLUDE;

		List<DTOStudentsExerciseNotTurnedInI> studentsExerciseNotTurnedIn = new ArrayList<>();
		userExerciseRepository.getNotifyNotTurnedInExercises(notTurnedInDays, exerciseStatusExclude).forEach(studentsExerciseNotTurnedIn::add);

		for(int i = 0; i < studentsExerciseNotTurnedIn.size(); i++) {

			Emails studentEmailNotificationLog = new Emails("ABSENCES");

			Student student = (Student) userRepository.findUserById(studentsExerciseNotTurnedIn.get(i).getId());
			Teacher teacher = (Teacher) userRepository.findUserById(studentsExerciseNotTurnedIn.get(i).getTeacher_id());

			studentEmailNotificationLog.setUserStudent(student);
			studentEmailNotificationLog.setSent(false);
			emailsRepository.save(studentEmailNotificationLog);

			String studentName = student.getFirstName() + " " + student.getLastName();
			String studentEmail = student.getEmail();
			String studentMessageBody = " I hope this e-mail finds you well.\n " +
					"This message is to inform you, " +
					"that it has been " +
					studentsExerciseNotTurnedIn.get(i).getDaysLastTurnedIn() +
					" days since you delivered any exercise";

			if(studentName != null && studentEmail != null) {
				try {
					studentEmailNotificationLog.setSent(true);
					emailsRepository.save(studentEmailNotificationLog);
					emailNotification(studentEmail, studentName, studentMessageBody);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}

			Emails teacherEmailNotificationLog = new Emails("ABSENCES");

			//teacherEmailNotificationLog.setUserStudent(teacher);
			teacherEmailNotificationLog.setSent(false);
			emailsRepository.save(teacherEmailNotificationLog);

			String teacherName = teacher.getFirstName() + " " + teacher.getLastName();
			String teacherEmail = teacher.getEmail();
			String teacherMessageBody = "The student " + student.getFirstName() + " " + student.getLastName() + "\n" +
					"has not delivered exercises for " + studentsExerciseNotTurnedIn.get(i).getDaysLastTurnedIn() + " days.";

			if(teacherName != null && teacherEmail != null) {
				try {
					teacherEmailNotificationLog.setSent(true);
					emailsRepository.save(teacherEmailNotificationLog);
					emailNotification(teacherEmail, teacherName, teacherMessageBody);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}


		}

	}


	/////////////////////////////////////////////   EMAIL NOTIFICATION METHOD    ///////////////////////////////////////////////////////////////


	public void emailNotification(String email, String name, String messageBody) throws Exception {

		MimeMessage message=javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);

		helper.setTo(email);
		helper.addBcc("barcelonaactiva.itacademy@gmail.com");										////// WRITE THE EMAIL FROM IT ACADEMY WHO WILL RECEIVE THE NOTIFICATIONS//////
		helper.setSubject("Barcelona Activa - IT Academy Program");
		String arg0 = new String(
				"<body class=\"respond\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">"+
						"    <!-- pre-header -->"+
						"    <table style=\"display:none!important;\">"+
						"        <tr>"+
						"            <td>"+
						"                <div style=\"overflow:hidden;display:none;font-size:1px;color:#343434;line-height:1px;font-family:Arial;maxheight:0px;max-width:0px;opacity:0;\">"+
						"                    Welcome to Barcelona Activa!"+
						"                </div>"+
						"            </td>"+
						"        </tr>"+
						"    </table>"+
						"    <!-- pre-header end -->"+
						"    <!-- header -->"+ 
						"    <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"9c9c9c\">"+
						""+
						"        <tr>"+
						"            <td align=\"center\">"+
						"                <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">"+
						""+
						"                    <tr>"+
						"                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\"> </td>"+
						"                    </tr>"+
						"                    "+
						"                    <tr>"+
						"                        <td align=\"center\">"+
						""+
						"                            <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">"+
						""+
						"                                <tr>"+
						"                                    <td align=\"center\" height=\"70\" style=\"height:70px;\">"+
						"                                        <a href=\"https://www.barcelonactiva.cat/barcelonactiva/cat/index.jsp\" style=\"display: block; border-style: none !important; border: 0 !important;\"><img width=\"150\" border=\"0\" style=\"display: block; width: 150px;\" src=\"https://www.barcelonactiva.cat/barcelonactiva/images/cat/BASA_LOGO_blanc_bottom_tcm101-44566.png\" alt=\"\" /></a>"+
						"                                    </td>"+
						"                                </tr>                               "+
						"                            </table>"+
						"                        </td>"+
						"                    </tr>"+
						""+
						"                    <tr>"+
						"                        <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\"> </td>"+
						"                    </tr>"+
						""+
						"                </table>"+
						"            </td>"+
						"        </tr>"+
						"    </table>"+
						"    <!-- end header -->"+
						""+
						"    <!-- big image section -->"+
						""+
						"    <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"ffffff\" class=\"bg_color\">"+
						""+
						"        <tr>"+
						"            <td align=\"center\">"+
						"                <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">"+
						""+
						"                    <tr>"+
						"                        <td align=\"center\" style=\"color: #343434; font-size: 24px; font-family: Quicksand, Calibri, sans-serif; font-weight:700;letter-spacing: 3px; line-height: 35px;\""+
						"                            class=\"main-header\">"+
						"                            <!-- section text ======-->"+
						""+
						"                            <div style=\"line-height: 35px\">"+
						""+
						"                                Welcome to <span style=\"color: #005E85;\">Barcelona Activa</span>!"+
						""+
						"                            </div>"+
						"                        </td>"+
						"                    </tr>"+
						""+
						"                    <tr>"+
						"                        <td height=\"10\" style=\"font-size: 10px; line-height: 10px;\"> </td>"+
						"                    </tr>"+
						""+
						"                    <tr>"+
						"                        <td align=\"center\">"+
						"                            <table border=\"0\" width=\"40\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"eeeeee\">"+
						"                                <tr>"+
						"                                    <td height=\"2\" style=\"font-size: 2px; line-height: 2px;\"> </td>"+
						"                                </tr>"+
						"                            </table>"+
						"                        </td>"+
						"                    </tr>"+
						""+
						"                    <tr>"+
						"                        <td height=\"20\" style=\"font-size: 20px; line-height: 20px;\"> </td>"+
						"                    </tr>"+
						""+
						"                    <tr>"+
						"                        <td align=\"left\">"+
						"                            <table border=\"0\" width=\"590\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">"+
						"                                <tr>"+
						"                                    <td align=\"left\" style=\"color: #888888; font-size: 16px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">"+
						"                                        <!-- section text ======-->"+
						""+
						"                                        <p style=\"line-height: 24px; margin-bottom:15px;\">"+
						""+
						name + ","+
						""+
						"                                        </p>"+
						"                                        <p style=\"line-height: 24px;margin-bottom:15px;\">"+
						messageBody +	
						"                                        </p>"+
						"                                        <p style=\"line-height: 24px; margin-bottom:20px;\">"+
						"                                            If you have any questions please aproach your course mentor."+
						"                                        </p>"+
						"                                        "+"</p>"+
						"                                        <p style=\"line-height: 24px\">"+
						"                                            Kind regards,"+"</p>"+
						"                                            Barcelona Activa team."+
						"                                        </p>"+
						""+
						"                                    </td>"+
						"                                </tr>"+
						"                            </table>"+
						"                        </td>"+
						"                    </tr>"+
						""+
						""+
						""+
						""+
						""+
						"                </table>"+
						""+
						"            </td>"+
						"        </tr>"+
						""+
						"        <tr>"+
						"            <td height=\"40\" style=\"font-size: 40px; line-height: 40px;\"> </td>"+
						"        </tr>"+
						""+
						"    </table>"+
						""+
						"    <!-- end section -->"+
						""+
						""+
						"    "+
						"    <!-- contact section -->"+
						"    <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"bebebe\" class=\"bg_color\">"+
						""+
						"        <tr>"+
						"            <td height=\"60\" style=\"font-size: 60px; line-height: 60px;\"> </td>"+
						"        </tr>"+
						""+
						"        <tr>"+
						"            <td align=\"center\">"+
						"                <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590 bg_color\">"+
						""+
						"                    <tr>"+
						"                        <td align=\"center\">"+
						"                            <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590 bg_color\">"+
						""+
						"                                <tr>"+
						"                                    <td>"+
						"                                        <table border=\"0\" width=\"300\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\""+
						"                                            class=\"container590\">"+
						""+
						"                                            <tr>"+
						"                                                <!-- logo -->"+
						"                                                <td align=\"left\">"+
						"                                                    <a href=\"https://www.barcelonactiva.cat/barcelonactiva/cat/index.jsp\" style=\"display: block; border-style: none !important; border: 0 !important;\"><img width=\"120\" border=\"0\" style=\"display: block; width: 120px;\" src=\"https://www.barcelonactiva.cat/barcelonactiva/images/cat/BASA_LOGO_blanc_bottom_tcm101-44566.png\" alt=\"\" /></a>"+
						"                                                </td>"+
						"                                            </tr>"+
						""+
						"                                            <tr>"+
						"                                                <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\"> </td>"+
						"                                            </tr>"+
						""+
						"                                            <tr>"+
						"                                                <td align=\"left\" style=\"color: #888888; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 23px;\""+
						"                                                    class=\"text_color\">"+
						"                                                    <div style=\"color: #333333; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; font-weight: 600; mso-line-height-rule: exactly; line-height: 23px;\">"+
						""+
						"                                                        Email us: <br/> <a href=\"mailto:barcelonactiva@barcelonactiva.cat\" style=\"color: #000000; font-size: 14px; font-family: 'Hind Siliguri', Calibri, Sans-serif; font-weight: 400;\">barcelonactiva@barcelonactiva.cat</a>"+
						""+
						"                                                    </div>"+
						"                                                </td>"+
						"                                            </tr>"+
						""+
						"                                        </table>"+
						""+
						"                                        <table border=\"0\" width=\"2\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\""+
						"                                            class=\"container590\">"+
						"                                            <tr>"+
						"                                                <td width=\"2\" height=\"10\" style=\"font-size: 10px; line-height: 10px;\"></td>"+
						"                                            </tr>"+
						"                                        </table>"+
						""+
						"                                        <table border=\"0\" width=\"200\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\""+
						"                                            class=\"container590\">"+
						""+
						"                                            <tr>"+
						"                                                <td class=\"hide\" height=\"45\" style=\"font-size: 45px; line-height: 45px;\"> </td>"+
						"                                            </tr>"+
						""+
						""+
						""+
						"                                            <tr>"+
						"                                                <td height=\"15\" style=\"font-size: 15px; line-height: 15px;\"> </td>"+
						"                                            </tr>"+
						""+
						"                                            <tr>"+
						"                                                <td>"+
						"                                                    <table border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"5\">"+
						"                                                        <tr>"+
						"                                                            <ul class=\"at_red_social\">"+
						"                                                                <td>"+
						"                                                                    <td><a href=\"https://www.instagram.com/barcelonactiva/\" title=\"Instagram\" target=\"_blank\"><img src=\"https://www.barcelonactiva.cat/barcelonactiva/images/cat/IG20x20_tcm101-51193.png\" width=\"20\" height=\"20\"></a></td>"+
						"                                                                </td>   "+
						"                                                                <td>"+
						"                                                                    <td><a href=\"https://www.facebook.com/pages/Barcelona-Activa/96851794479\" title=\"Facebook\" target=\"_blank\"><img src=\"https://www.barcelonactiva.cat/barcelonactiva/images/cat/FB20x20_tcm101-51192.png\" width=\"20\" height=\"20\"></a></td>"+
						"                                                                </td>    "+
						"                                                                <td>    "+
						"                                                                    <td><a href=\"http://www.youtube.com/videosbarcelonactiva\" title=\"YouTube\" target=\"_blank\"><img src=\"https://www.barcelonactiva.cat/barcelonactiva/images/cat/YT20x20_tcm101-51198.png\" width=\"20\" height=\"20\"></a></td>"+
						"                                                                </td>    "+
						"                                                                <td>"+
						"                                                                    <td><a href=\"http://twitter.com/barcelonactiva\" title=\"Twitter\" target=\"_blank\"><img src=\"https://www.barcelonactiva.cat/barcelonactiva/images/cat/TW20x20_tcm101-51197.png\" width=\"20\" height=\"20\"></a></td>                                                                      "+
						"                                                                </td>"+
						"                                                                <td>"+
						"                                                                    <td><a href=\"http://www.slideshare.net/barcelonactiva\" title=\"SlideShare\" target=\"_blank\"><img src=\"https://www.barcelonactiva.cat/barcelonactiva/images/cat/SS20x20_tcm101-51196.png\" width=\"20\" height=\"20\"></a></td>"+
						"                                                                </td>"+
						"                                                                <td>    "+
						"                                                                    <td><a href=\"http://www.linkedin.com/company/barcelona-activa/\" title=\"Linkedin\" target=\"_blank\"><img src=\"https://www.barcelonactiva.cat/barcelonactiva/images/cat/IN20x20_tcm101-51194.png\" width=\"20\" height=\"20\"></a></td>"+
						"                                                                </td>"+
						"                                                                <td>    "+
						"                                                                    <td><a href=\"/barcelonactiva/cat/utils/homeRss.jsp\" title=\"RSS\"><img src=\"https://www.barcelonactiva.cat/barcelonactiva/images/cat/RSS20x20_tcm101-51195.png\" width=\"20\" height=\"20\"></a></td>"+
						"                                                                </td>"+
						"                                                            </ul>"+
						"                                                        </tr>"+
						"                                                    </table>"+
						"                                                </td>"+
						"                                            </tr>"+
						""+
						"                                        </table>"+
						"                                    </td>"+
						"                                </tr>"+
						"                            </table>"+
						"                        </td>"+
						"                    </tr>"+
						"                </table>"+
						"            </td>"+
						"        </tr>"+
						""+
						"        <tr>"+
						"            <td height=\"60\" style=\"font-size: 60px; line-height: 60px;\"> </td>"+
						"        </tr>"+
						""+
						"    </table>"+
						"    <!-- end section -->"+
						""+
						"    <!-- footer ====== -->"+
						"    <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"f4f4f4\">"+
						""+
						"        <tr>"+
						"            <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\"> </td>"+
						"        </tr>"+
						""+
						"        <tr>"+
						"            <td align=\"center\">"+
						""+
						"                <table border=\"0\" align=\"center\" width=\"590\" cellpadding=\"0\" cellspacing=\"0\" class=\"container590\">"+
						""+
						"                    <tr>"+
						"                        <td>"+
						"                            <table border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\""+
						"                                class=\"container590\">"+
						"                                <tr>"+
						"                                    <td align=\"left\" style=\"color: #aaaaaa; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">"+
						"                                        <div style=\"line-height: 24px;\">"+
						""+
						"                                            <span style=\"color: #333333;\">© 1997-2020 Barcelona Activa</span>"+
						""+
						"                                        </div>"+
						"                                    </td>"+
						"                                </tr>"+
						"                            </table>"+
						""+
						"                            <table border=\"0\" align=\"left\" width=\"5\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\""+
						"                                class=\"container590\">"+
						"                                <tr>"+
						"                                    <td height=\"20\" width=\"5\" style=\"font-size: 20px; line-height: 20px;\"> </td>"+
						"                                </tr>"+
						"                            </table>"+
						"                            "+
						"                            <table border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\""+
						"                                class=\"container590\">"+
						"                                <tr>"+
						"                                    <td align=\"left\" style=\"color: #aaaaaa; font-size: 14px; font-family: 'Work Sans', Calibri, sans-serif; line-height: 24px;\">"+
						"                                        <div style=\"line-height: 24px;\">"+
						""+
						"                                            <span style=\"color: #333333;\">Barcelona Activa Seu Central"+
						"                                                Llacuna, 162 - 164"+
						"                                                08018 Barcelona"+
						"                                                +34 934 019 777</span>"+
						""+
						"                                        </div>"+
						"                                    </td>"+
						"                                </tr>"+
						"                            </table>"+
						""+
						"                            "+
						"                        </td>"+
						"                    </tr>"+
						""+
						"                </table>"+
						"            </td>"+
						"        </tr>"+
						""+
						"        <tr>"+
						"            <td height=\"25\" style=\"font-size: 25px; line-height: 25px;\"> </td>"+
						"        </tr>"+
						""+
						"    </table>"+
						"    <!-- end footer ====== -->"+
						""+
				"</body>");
		helper.setText(arg0, true);
		javaMailSender.send(message);
	}




}
