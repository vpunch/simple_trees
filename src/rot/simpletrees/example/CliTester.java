package rot.simpletrees.example;

import rot.simpletrees.model.*;

import java.util.Scanner;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class CliTester {

	/*
	while( !sc.hasNext(ptrn) ) {
		sc.next();
		System.out.printf("%s\n", fail);
	}
	return sc.next();
	*/

	static Integer getKey(String cmd) 
	{
		return Integer.parseInt(cmd.replaceAll("\\D+", ""));
	}

	static public void main(String[] args) 
	{
		String help =
		    " insert KEY   - insert two-digit number KEY to tree"
		+ "\n remove KEY   - remove KEY from tree"
		+ "\n search KEY   - search KEY within tree"
		+ "\n size         - print tree size (node count)"
		+ "\n height       - print tree height"
		+ "\n print        - print tree"
		+ "\n list         - print data list"
		+ "\n quit         - exit tester";

		String kph = "  "; //key place holder
		String welcome = 
			  "FibTree cli tester\n"
			+ "Type \'help\' to view a list of commands.";	
		String goodbye =
			"Bye.";
		String prompt =
			"(fibtree) ";

		FibTree<Integer, Integer> tree = new FibTree<>();
		Scanner sc = new Scanner(System.in);

		System.out.printf("%s\n", welcome);

		while(true) {
			System.out.printf("%s", prompt);
			String cmd = sc.nextLine().trim();

			if(cmd.isEmpty()) continue;

			if( cmd.equals("quit") ) break;

			else if( cmd.equals("help") )
				System.out.printf("%s\n", help);

			else if( cmd.matches("insert \\d{1,2}") ) {
				Integer key = getKey(cmd); 
				tree.insert(new FibTree.Data<Integer, Integer>(key, 0));
			}

			else if( cmd.equals("print") ) {
				FibTree.DataLevelsList<Integer, Integer> dls = tree.getDataLevels(); //data levels
				
				int levels = dls.size(); // levels
				int terms = (int) Math.pow(2, levels-1); // терминальных вершин

				for(int i = 0; i < dls.size(); ++i) {

					int curLevel = i+1; // current level (уровни нумеруются начиная с единицы)
					int indent = (int)( terms/Math.pow(2, curLevel-1) ) - 1; // отступ с начала и с конца
					int gap = indent*2+1; // зазор между вершинами

					for(int j = 0; j < dls.get(i).size(); ++j) {

						if(j == 0)
							for( int k = 0; k < indent; ++k )
								System.out.print("  ");
						else 
							for( int k = 0; k < gap; ++k)
								System.out.print("  ");

						FibTree.Data data = dls.get(i).get(j);
						if(data != null) {
							int key = ((Integer)data.m_key).intValue();
							if(Math.ceil(Math.log10(key))<2)
								System.out.print("_");
							System.out.print(key);
						} else
							System.out.print("  ");
					}
					System.out.println();
				}
			}

			else if( cmd.equals("list") ) {
				ArrayList<BinTree.Data<Integer, Integer>> list = tree.getDataList();
				if( list.size() != 0) {
					for(int i = 0; i < list.size(); ++i)
						System.out.print(list.get(i).m_key + " ");	
					System.out.println();
				}
			} 
			
			else if( cmd.equals("size") ) {
				System.out.println(tree.size());
			} else if( cmd.equals("height") ) {
				System.out.println(tree.height());
			}

			else if ( cmd.matches("search \\d{1,2}") ) {
				Integer key = getKey(cmd);
				BinTree.Data<Integer, Integer> result = tree.search(key);
				if( result == null )
					System.out.println("Not found");
				else System.out.println("Is present");
			}

			else if ( cmd.matches("remove \\d{1,2}") ) {
				Integer key = getKey(cmd);
				tree.remove(key);
			}

			else System.out.println(help);
		}

		System.out.printf("%s\n", goodbye);
	}
}
