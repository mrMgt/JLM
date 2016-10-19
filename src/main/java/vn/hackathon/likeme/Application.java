package vn.hackathon.likeme;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.hackathon.likeme.entity.Hashtag;
import vn.hackathon.likeme.repository.HashtagRepository;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class Application{
	private static final Logger log = LoggerFactory.getLogger(Application.class);


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*@Bean
	public CommandLineRunner run(HashtagRepository hashtagRepository) throws Exception {

		return (args) -> {
			long count = hashtagRepository.count();
			if(count > 0){
				return;
			}
			List<Hashtag> hashtags = new ArrayList<>();
			URI fileName =  getClass().getClassLoader().getResource("hash.txt").toURI();
			try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
				stream.forEach(str -> {
					log.info(str);
					Hashtag hash = new Hashtag(str,"");
					hashtags.add(hash);
				});
				hashtagRepository.save(hashtags);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}*/
}

