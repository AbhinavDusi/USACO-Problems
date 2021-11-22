import math; 
result = ""; 
for i in range(0, 12): 
	fname = "/Users/abhinavdusi/Desktop/" + str(i + 1) + ".txt"; 
	fin = open(fname, "rw+");
	lines = fin.read().splitlines(); 
	N = int(lines[0]); 
	X, Y, iX, iY = 0, 0, 0, 0;  
	for j in range(0, N):
		d = lines[j + 1]; 
		if (d == "north"): #south
			Y = Y - 1; 
			iY = iY + 1; 
		if (d == "south"): #north
			Y = Y + 1; 
			iY = iY - 1; 
		if (d == "east"): #west
			X = X - 1; 
			iX = iX + 1; 
		if (d == "west"): #east
			X = X + 1; 
			iX = iX - 1; 
		if (d == "northeast"): #southwest
			X = X - 1; 
			iX = iX + 1;
			Y = Y - 1; 
			iY = iY + 1;
		if (d == "northwest"): #southeast
			X = X + 1;
			iX = iX - 1; 
			Y = Y - 1; 
			iY = iY + 1;
		if (d == "southeast"): #northwest
			X = X - 1;
			iX = iX + 1; 
			Y = Y + 1; 
			iY = iY - 1; 
		if (d == "southwest"): #northeast
			X = X + 1; 
			iX = iX - 1;
			Y = Y + 1; 
			iY = iY - 1;
	distance = int(round(math.sqrt((X - iX) ** 2 + (Y - iY) ** 2))); 
	mod = distance % 26; 
	letter = chr(mod + 65); 
	result = result + letter; 
print(result.lower()); 
fin.close(); 