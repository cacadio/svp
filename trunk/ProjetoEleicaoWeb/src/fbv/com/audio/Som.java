package fbv.com.audio;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Som extends Thread {   

	public static void tocar(){   
//		String caminhoSom = "D:\\MÚSICAS\\NACIONAIS\\Efeito_Urna_Eletronica.wav";
		String caminhoSom = "C:/eclipse-Ganymede-com-ME/workspace/ProjetoEleicaoWeb/build/classes/fbv/com/audio/Efeito_Urna_Eletronica.wav"; 
		tocar(caminhoSom);
		
	}
	public static void tocar(String arquivo){   
		try {   
			// Arquivo   
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(arquivo));   


			//Se a codificação for diferente de PCM_SIGNED, então   
			//connverte o arquivo antes de ser tocado   
			AudioFormat format = stream.getFormat();   
			if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {      
				format = new AudioFormat(   
						AudioFormat.Encoding.PCM_SIGNED,   
						format.getSampleRate(),   
						format.getSampleSizeInBits()*2,   
						format.getChannels(),   
						format.getFrameSize()*2,   
						format.getFrameRate(),true);   

				//Passa o arquivo já codificado para stream   
				stream = AudioSystem.getAudioInputStream(format, stream);   
			}   

			// Cria um clip   
			DataLine.Info info = new DataLine.Info(   
					Clip.class,    
					stream.getFormat(),    
					((int)stream.getFrameLength()*format.getFrameSize())   
			);   
			Clip clip = (Clip) AudioSystem.getLine(info);   

			//Carrega o arquivo de audio   
			clip.open(stream);   

			//Toca o arquivo de audio   
			clip.start();   
			
			Thread.sleep (2000); 

		} catch (Exception e) {   
			e.printStackTrace();
		}   
	}   
}  