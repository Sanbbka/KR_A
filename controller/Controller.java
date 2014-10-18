package controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

	
	public static final int  N = 5;
	public static final HashSet<String> users = new HashSet<String>();
	public static final Hashtable< String, HashSet<String> > colorMails = new Hashtable< String, HashSet<String> >();
	
	
	
	public static int start(String path) throws IOException{
		
		users.clear();
		colorMails.clear();
		for(int i = 0; i < N; i++ ){
			load(path + "/" + i + ".txt");
		}
		
		return users.size();
	}
	
	public static void gload(String path) throws IOException{
		
		List<String> a = new ArrayList<String>();
		
		Path p = Paths.get(path);
		
		if( !Files.exists(p) )
			Files.createFile(p);
		
		Random r = new Random();
		
		int n = r.nextInt(25);
		
		String g[] = {"@gmail","@list","@yandex","@mail"};
		String dot[] = {".com",".ru"};
		
		
		for( int i = 0; i < n; i++ ){
			String s = new String();
			int m = 1 + r.nextInt(15);
			for( int j = 0; j < m; j++ ){
				if(r.nextBoolean())
					s+= (char)('a' +  r.nextInt('z'-'a' + 1 ));
				else if(r.nextBoolean())
					s+= r.nextInt(10);
				else
					s+= (char)('A' +  r.nextInt('Z'-'A' + 1 ));
			}
			s+=" ";
			m = 1 + r.nextInt(15);
			for( int j = 0; j < m; j++ ){
				if(r.nextBoolean())
					s+= (char)('a' +  r.nextInt('z'-'a' + 1 ));
				else if(r.nextBoolean())
					s+= r.nextInt(10);
				else
					s+= (char)('A' +  r.nextInt('Z'-'A' + 1 ));
			
			}	
			s+=g[r.nextInt(g.length)];
			s+=dot[r.nextInt(dot.length)];
			s+=" rgb(" + r.nextInt(255) + "," + r.nextInt(255) + "," + r.nextInt(255) + ")";
			a.add(s);
		}
		
		Files.write(p, a, Charset.defaultCharset());
		
		
	}
	
	
	static void load(String path) throws IOException{
		
		Path p = Paths.get(path);
		
		
		List<String> lines = Files.readAllLines(p,Charset.defaultCharset());
		
		for( String s : lines ){	
			StringTokenizer t = new StringTokenizer(s," ");
			
			Pattern user  = Pattern.compile("[a-zA-Z][a-zA-Z0-9]{2,15}");
			Pattern mail  = Pattern.compile("[a-zA-Z][a-zA-Z0-9]{4,15}@(gmail|yandex|list)\\.(ru|com)");
			Pattern color = Pattern.compile("rgb\\(([01]?\\d\\d?|)(,[01]?\\d\\d?|,2[0-4]\\d|\\,25[0-5]){2}\\)");
			
			Matcher mUser  = user.matcher(t.nextToken());
			Matcher mMail  = mail.matcher(t.nextToken());
			Matcher mColor = color.matcher(t.nextToken());
			
			
			if(mUser.find() && mMail.find() && mColor.find() ){
			
				users.add(mUser.group());
				
				String f = mColor.group();
				if(!colorMails.contains(s)){
					HashSet<String> h = new HashSet<String>();
					h.add(mMail.group());
					colorMails.put(f, h);
				}else{
					colorMails.get(f).add(mMail.group());
				}
				
			}
			
		}
		
	}
	
	static public void sort() throws IOException{
		Set<String> keys = colorMails.keySet();
		
		
		for( String s : keys ){
			
			HashSet<String> mails = colorMails.get(s);
			Path p = Paths.get("output/"+s.replace(',','.'));
			
			Files.createFile(p);
			
			Files.write(p, mails, Charset.defaultCharset());
			
		}
	
	}
	
}
