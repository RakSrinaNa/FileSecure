package fr.raksrinana.filesecure.config.options.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.raksrinana.filesecure.config.FileOption;
import fr.raksrinana.filesecure.exceptions.AbandonBackupException;
import fr.raksrinana.filesecure.files.DesiredTarget;
import fr.raksrinana.nameascreated.NewFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import java.nio.file.Path;
import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("IfOlderThanOption")
@Slf4j
@NoArgsConstructor
public class IfOlderThanOption implements FileOption{
	@JsonProperty(value = "dayOffset", required = true)
	@Getter
	private int dayOffset = Integer.MAX_VALUE;
	
	@Override
	public void apply(@NonNull final Path originFile, @NonNull final DesiredTarget desiredTarget, @NonNull final NewFile fileName, @NonNull final Path folder) throws AbandonBackupException{
		try{
			final var date = fileName.getDate();
			if(date.isAfter(ZonedDateTime.now().minusDays(this.getDayOffset()))){
				throw new AbandonBackupException(originFile);
			}
		}
		catch(final Exception e){
			if(e instanceof AbandonBackupException){
				throw e;
			}
			log.error("Failed to determine if {} should be backed up, it will be by default", originFile, e);
		}
	}
	
	@Override
	public int getPriority(){
		return 0;
	}
}