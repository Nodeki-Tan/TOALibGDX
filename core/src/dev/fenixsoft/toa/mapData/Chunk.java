package dev.fenixsoft.toa.mapData;

import dev.fenixsoft.toa.core.MapCore;

public class Chunk {
	
	private short[] tiles;

	private boolean isSaved = true;

	public Chunk(short[] tiles) {
		this.tiles = tiles;
	}

	public short getTile(int x, int y) {
		
		if(x <= -1) {
			x += MapCore.CHUNK_WIDTH;
		}
		
		if(y <= -1) {
			y += MapCore.CHUNK_WIDTH;
		}

		//System.out.println("Tile in [" + x + "," + y + "]");
		
		int index = x + (y * MapCore.CHUNK_WIDTH);
		
		return tiles[index];
	}
	
	public void setTile(int x, int y, short value) {
		
		if(x <= -1) {
			x += MapCore.CHUNK_WIDTH;
		}
		
		if(y <= -1) {
			y += MapCore.CHUNK_WIDTH;
		}

		//System.out.println("Tile in [" + x + "," + y + "]");
		
		int index = x + (y * MapCore.CHUNK_WIDTH);
		
		tiles[index] = value;
		
		isSaved = false; 
	}

	public short[] getTiles() {
		return tiles;
	}

	public void setSaved() {
		isSaved = true;
	}

	public boolean getSaved() {
		return isSaved;
	}

}
