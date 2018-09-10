#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define memCard DP[notAble][i]

int acoes, custo;
int DP[2][(int) 2e5];
int ac[(int) 2e5];

int max(int a, int b)
{
  return(a > b ? a : b);
}

int dp(int i, int notAble)
{
  if (i == acoes)
  {
    return(0);
  }
  if (memCard == -1)
  {
    int aux;
    memCard = dp(i + 1, notAble); //N?o faz nada
    if (notAble == 0)
    {
      aux = dp(i + 1, 1) - ac[i] - custo; //Compra a a??o
      memCard = max(memCard, aux); //Escolhe o melhor
    }
    if (notAble == 1)
    {
      aux = dp(i + 1, 0) + ac[i]; //Vende a a??o
      memCard = max(memCard, aux); //Escolhe o melhor
    }
  }
  return(memCard); //Retorna o valor m?ximo
}

int main()
{
  scanf("%d %d", &acoes, &custo);

  int i;
  memset(DP, -1, sizeof(DP));

  for (i = 0; i < acoes; i ++)
    scanf("%d", &ac[i]);

  int ans = dp(0, 0);
  printf("%d\n", ans);
  return(0);
}
