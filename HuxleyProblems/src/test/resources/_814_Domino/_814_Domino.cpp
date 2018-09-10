#include <stdio.h>
#include <string.h>
int graph[7][7];

void conexAux(int source, int visited[])
{
  if (visited[source]) return;
  visited[source] = 1;
  int i;
  for (i = 0; i < 7; i ++)
    if (graph[source][i])
      conexAux(i, visited);
}

int conex(int pieces[])
{
  int i, j;
  for (i = 0; i < 7; i ++)
    if (pieces[i])
    {
      int visited[7]; memset(visited, 0, sizeof(visited));
      conexAux(i, visited);
      for (j = 0; j < 7; j ++) if (pieces[j] && !visited[j]) return(0);
      return(1);
    }
  return(0);
}

int main()
{
  int n, r = 1;
  while (scanf("%d", &n) != EOF && n)
  {
    memset(graph, 0, sizeof(graph));
    int pieces[7]; memset(pieces, 0, sizeof(pieces));
    int u, v, i;
    for (i = 0; i < n; i ++)
    {
      scanf("%d %d", &u, &v);
      graph[u][v] = graph[v][u] = 1;
      pieces[u] ++; pieces[v] ++;
    }
    int odd = 0;
    for (i = 0; i < 7; i ++) odd += pieces[i] & 1;
    printf("Teste %d\n%s\n\n", r ++, (odd > 2 || !conex(pieces)) ? "nao" : "sim");
  }
  return(0);
}
