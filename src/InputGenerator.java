import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class InputGenerator {
	private static String INF = "\u221E";
	private static int max = 10;
	private static int max_size = 10;
	private static int min_size = 3;
	// define charset
	private static Charset charset = Charset.forName("UTF-8");

	public static void main(String[] args) {
		// set up output path
		Path output_path = FileSystems.getDefault().getPath("./input", "input.csv");
		
		try {
			// make writer for output file
			BufferedWriter writer = Files.newBufferedWriter(output_path, charset);
			// st_writer prints to console
			BufferedWriter st_writer = new BufferedWriter(new OutputStreamWriter(System.out));
			
			// generate random matrix size
			Random random = new Random();
			int matrix_size = random.nextInt((max_size - min_size) + 1) + min_size;
			
			String[][] matrix = new String[matrix_size][matrix_size];
			matrix = non_symmetric(matrix, random);
			print(matrix, st_writer);
			print(matrix, writer);
			
			st_writer.write("\n");
			writer.write("\n");
			
			matrix = symmetric(matrix, random);
			print(matrix, st_writer);
			print(matrix, writer);
			
			st_writer.close();
			writer.close();
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}
	
	private static String[][] non_symmetric(String[][] matrix, Random random){
		// initialize array
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				// randomly assign either an int or INF to the current location
				if ((random.nextInt((max_size - min_size) + 1) + min_size) % 3 == 0) {
					matrix[i][j] = "" + random.nextInt(max + 1);
				}
				else {
					matrix[i][j] = "" + INF;
				}
			}
		}
		return matrix;
	}
	
	private static String[][] symmetric(String[][] matrix, Random random){
		// initialize array
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				// guarantee symmetry by copying the half above the diagonal to the half below the diagonal
				if (i - j < 0) {
					// randomly assign either an int or INF to the current location
					if ((random.nextInt((max_size - min_size) + 1) + min_size) % 3 == 0) {
						matrix[i][j] = "" + random.nextInt(max + 1);
					}
					else {
						matrix[i][j] = "" + INF;
					}
				}
				else {
					matrix[i][j] = "" + matrix[j][i];
				}
				
			}
		}
		return matrix;
	}
	
	/**
	 * Prints the given matrix (to the given writer) separated by commas
	 * @param matrix
	 * @param writer
	 * @throws IOException
	 */
	private static void print(String[][] matrix, BufferedWriter writer) throws IOException {
		for (String[] s : matrix) {
			for (int i=0; i<s.length - 1; i++) {
				writer.write(s[i]+ ",");
			}
			writer.write(s[s.length - 1] + "\n");
		}
	}

}
