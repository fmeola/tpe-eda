package flow.FrontEnd;

import java.io.IOException;

public class Game {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		boolean progress = false;
		int maxTime = 0;
		boolean BestSolution = true;
		String FileRoute;
		if (args.length >= 1)
			FileRoute = args[0];
		else
			return;
		if (args.length >= 2)
			if (args[1].equals("exact"))
				BestSolution = true;
			else if (args[1].equals("approx"))
				BestSolution = false;
			else
				return;
		if (args.length >= 3)
			if (!BestSolution)
				maxTime = Integer.parseInt(args[2]);
			else if (args[2].equals("progress"))
				progress = true;
			else
				return;
		if (args.length >= 4)
			if (args[3].equals("progress"))
				progress = true;
			else
				return;
		GameFrame main = new GameFrame("Flow", FileRoute, BestSolution,
				maxTime, progress);
		main.setVisible(true);
	}
}
