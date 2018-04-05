import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

/*
Authors: Alex Harry, Cory Johns, Justin Keeling
Date: April 4, 2018
Overview: Program generates a specified number of matrixes of random size and contents 
and can generate symmetric, non-symmetric, and non-cyclic matrixes. Outputs results to the console and ./input/input.csv.
Graphs may not be connected
*/
public class InputGenerator {
	// string to represent infinity
	private static String INF = "\u221E";
	// max value of a weight
	private static int max = 10;
	// max size of a matrix
	private static int max_size = 10;
	// min size of a matrix
	private static int min_size = 3;
	// number of matrix pairs to print
	private static int pairs = 1;
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
			
			// make an empty matrix
			String[][] matrix = new String[matrix_size][matrix_size];
			
			// repeat a select number of times
			for (int i=0; i<pairs; i++) {
				// randomly pick a type of matrix
				int random_selector = random.nextInt(100) % 3;
				if (random_selector == 0) {
					matrix = non_symmetric(matrix, random);
				}
				else if (random_selector == 1) {
					matrix = symmetric(matrix, random);
				}
				else {
					matrix = non_cyclic(matrix, random);
				}
				
				// print matrix to both outputs
				print(matrix, st_writer);
				print(matrix, writer);
				
				// separate with a newline
				st_writer.write("\n");
				writer.write("\n");
			}
			
			// close outputs
			st_writer.close();
			writer.close();
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}
	
	/**
	 * Makes a non-symmetric matrix by randomly adding either a int or INF to each index
	 * @param matrix
	 * @param random
	 * @return the initialized matrix
	 */
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
	
	/**
	 * Makes a symmetric matrix by copying the half above the diagonal to the half below the diagonal
	 * @param matrix
	 * @param random
	 * @return the initialized matrix
	 */
	private static String[][] symmetric(String[][] matrix, Random random){
		// initialize array
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				// guarantee symmetry by copying the half above the diagonal to the half below the diagonal
				if (i - j <= 0) {
					// randomly assign either an int or INF to the current location
					if ((i - j != 0) && (random.nextInt((max_size - min_size) + 1) + min_size) % 3 == 0) {
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
	 * Makes a noncyclic matrix by only adding edges to higher/lower vertexes but never both
	 * @param matrix
	 * @param random
	 * @return the initialized matrix
	 */
	private static String[][] non_cyclic(String[][] matrix, Random random){
		// pick a random side of the diagonal, random_side will be ether a 0 or a 1
		int random_side = random.nextInt(100) % 2;
		
		// initialize array
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[i].length; j++) {
				// guarantee noncyclic by not including values for one side of the diagonal
				// if random_side is a 0 the upper side will be picked
				if ((i - j)*(1 - 2*random_side) < 0) {
					// randomly assign either an int or INF to the current location
					if ((random.nextInt((max_size - min_size) + 1) + min_size) % 3 == 0) {
						matrix[i][j] = "" + random.nextInt(max + 1);
					}
					else {
						matrix[i][j] = "" + INF;
					}
				}
				else {
					matrix[i][j] = "" + INF;
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
		// print leading a,b,c,...
		for (int i=0; i<matrix.length - 1; i++) {
			writer.write((char)(65 + i) + ",");
		}
		writer.write((char)(64 + matrix.length) + "\n");
		
		for (String[] s : matrix) {
			for (int i=0; i<s.length - 1; i++) {
				writer.write(s[i]+ ",");
			}
			writer.write(s[s.length - 1] + "\n");
		}
	}

}
