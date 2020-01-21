package fr.raksrinana.filesecure.files;

import fr.raksrinana.nameascreated.NewFile;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.nio.file.Path;

public class DesiredTarget{
	@Getter
	private final NewFile newFileInfos;
	@Getter
	@Setter
	private Path targetFolder;
	@Getter
	@Setter
	private String desiredName;
	
	/**
	 * Constructor.
	 *
	 * @param targetFolder The target folder (where to copy/move/...).
	 * @param desiredName  A pair describing the file name in the base (key) and in the target (value).
	 */
	public DesiredTarget(@NonNull final Path targetFolder, @NonNull final NewFile newFileInfos, @NonNull final String desiredName){
		this.targetFolder = targetFolder;
		this.desiredName = desiredName;
		this.newFileInfos = newFileInfos;
	}
}
