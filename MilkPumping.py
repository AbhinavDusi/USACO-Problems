import heapq 
import sys
import math 
fin = open('pump.in', 'r')
fout = open('pump.out', 'w')
line = fin.readline().split()
N = int(line[0]) 
M = int(line[1]) 
adjList = [[] for i in range(0, N + 1)]
def add_edge(start, end, cost, flow_rate):
    adjList[start].append((flow_rate, cost, end)) 
    adjList[end].append((flow_rate, cost, start)) 
def dijkstra(src, dest):
    settled = set() 
    cost = [sys.maxsize] * (N + 1) 
    flow_rate = [-1] * (N + 1) 
    queue = [] 
    cost[src] = 0 
    flow_rate[src] = sys.maxsize 
    heapq.heappush(queue, (-sys.maxsize, src)) 
    while len(settled) != N:
        curr = heapq.heappop(queue)
        settled.add(curr[1]) 
        for i in range(0, len(adjList[curr[1]])):
            neighbor = adjList[curr[1]][i] 
            if neighbor[2] not in settled:
                ratio = min(flow_rate[curr[1]], neighbor[0]) / (cost[curr[1]] + neighbor[1]) 
                if ratio > flow_rate[neighbor[2]] / cost[neighbor[2]]:
                    flow_rate[neighbor[2]] = min(flow_rate[curr[1]], neighbor[0]) 
                    cost[neighbor[2]] = cost[curr[1]] + neighbor[1] 
                heapq.heappush(queue, (-ratio, neighbor[2]))
    return flow_rate[dest] / cost[dest] 
for i in range(0, M):
    line = fin.readline().split() 
    add_edge(int(line[0]), int(line[1]), int(line[2]), int(line[3]))
fout.write(str(math.floor(dijkstra(1, N) * 1000000)))
fin.close()
fout.close() 