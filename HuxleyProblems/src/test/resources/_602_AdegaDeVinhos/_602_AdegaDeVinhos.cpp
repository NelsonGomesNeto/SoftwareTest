#include <stdio.h>
#include <string.h>
#define memCard DP[start - 1][end - 1]
#define vendeComeco dp(w, ano + 1, start + 1, end) + (w[start] * ano)
#define vendeFim dp(w, ano + 1, start, end - 1) + (w[end] * ano)
int DP[301][301];

int vinhos;

int max(int a, int b)
{
  return(a > b ? a : b);
}

int dp(int w[], int ano, int start, int end)
{
  if (ano == vinhos)
    return(w[start] * ano);

  if (memCard == -1)
    memCard = max(vendeComeco, vendeFim);

  return(memCard);
}

int main()
{
  while (scanf("%d", &vinhos) != EOF)
  {
    memset(DP, -1, sizeof(DP));
    int v[vinhos + 1], i;
    for (i = 1; i <= vinhos; i ++)
      scanf("%d", &v[i]);
    int ans = dp(v, 1, 1, vinhos);
    printf("%d\n", ans);
  }
  return(0);
}
