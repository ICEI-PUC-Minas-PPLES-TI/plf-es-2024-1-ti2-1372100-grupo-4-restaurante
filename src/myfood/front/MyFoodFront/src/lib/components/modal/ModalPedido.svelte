<script lang="ts">
  import * as Dialog from "$lib/components/ui/dialog/index.js";
  import * as Card from "$lib/components/ui/card";
  import { Input } from "$lib/components/ui/input/index.js";
  import { Label } from "$lib/components/ui/label/index.js";
  import { Button, buttonVariants } from "$lib/components/ui/button";
  import { Plus, Minus } from "lucide-svelte";
  import { Rating } from "flowbite-svelte";
  import { onMount } from "svelte";
  import { ENDPOINT_URL } from "$lib/constants";
  import { cart } from "$lib/cartStore";

  let quantidade = 1;

  export let id: number;
  export let nome: string;
  export let preco: number;
  export let descricao: string;
  export let imagem: string;
  export let visibilidadeAvaliacao: boolean;
  export let avaliacao: { id: number; produtoId: number; estrelas: number }[] =
    [];
  let precoTotal = preco;

  function increase() {
    quantidade += 1;
    precoTotal += preco;
  }

  function decrease() {
    if (quantidade > 1) {
      quantidade -= 1;
      precoTotal -= preco;
    }
  }

  function addToCart() {
    cart.update((items) => {
      const updatedItems = [...items, { id, nome, quantidade, precoTotal }];
      console.log("Cart after adding item:", updatedItems);
      return updatedItems;
    });
  }

  async function fetchAvaliacao() {
    try {
      const response = await fetch(`${ENDPOINT_URL}/avaliacao`);
      if (!response.ok) {
        throw new Error("Falha fetch nas avaliacoes");
      }
      avaliacao = await response.json();
    } catch (error) {
      console.error(error);
    }
  }

  function calculaMediaAvaliacao(
    avaliacoes: { produtoId: number; estrelas: number }[],
    produtoId: number
  ) {
    const avaliacoesProduto = avaliacoes.filter(
      (avaliacao) => avaliacao.produtoId === produtoId
    );
    if (avaliacoesProduto.length === 0) {
      return 0;
    }
    const totalStars = avaliacoesProduto.reduce(
      (acc, avaliacao) => acc + avaliacao.estrelas,
      0
    );
    return totalStars / avaliacoesProduto.length;
  }
  let mediaAvaliacao = 0;

  onMount(async () => {
    await fetchAvaliacao();
    mediaAvaliacao = calculaMediaAvaliacao(avaliacao, id);
  });
</script>

<main class="mt-5 flex items-center justify-center">
  <Dialog.Root>
    <Dialog.Trigger>
      <Card.Root class="w-auto">
        <div class="row flex">
          <div class="column w-7/12">
            <Card.Header>
              <Card.Title
                class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white"
                >{nome}</Card.Title
              >
            </Card.Header>
            <Card.Content class="flex items-center">
              <div class="flex-grow">
                <p class="mb-3 font-normal leading-tight text-gray-700">
                  Descricão: {descricao}
                </p>
                {#if visibilidadeAvaliacao == true}
                  <div class=" flex justify-center">
                    <Rating
                      count
                      rating={parseFloat(mediaAvaliacao.toFixed(1))}
                      id="example-4"
                    >
                      <span class="w-1 h-1 mx-1.5 bg-gray-500 rounded-full" />
                      <p class="text-sm font-medium text-gray-900">
                        {#if avaliacao.filter((av) => av.produtoId === id).length == 1}
                          {avaliacao.filter((av) => av.produtoId === id).length}
                          avaliação
                        {:else}
                          {avaliacao.filter((av) => av.produtoId === id).length}
                          avaliações
                        {/if}
                      </p>
                    </Rating>
                  </div>
                {/if}
                <p class="text-2xl font-bold text-gray-900 dark:text-white">
                  R${preco}
                </p>
              </div>
            </Card.Content>
          </div>
          <div class="column w-5/12 h-auto">
            <img
              src={imagem}
              alt=""
              class="ml-auto h-full w-60 rounded-r-lg object-cover"
            />
          </div>
        </div>
      </Card.Root>
    </Dialog.Trigger>
    <Dialog.Content class="sm:max-w-[600px]">
      <Dialog.Header>
        <Dialog.Title
          >Adicionar <span class="font-bold text-primary">{nome}</span> ao seu pedido</Dialog.Title
        >
        <Dialog.Description>
          Selecione a quantidade e clique em adicionar!
        </Dialog.Description>
      </Dialog.Header>
      <div class="grid gap-4 py-4">
        <div class=" h-10 w-10 items-center justify-center gap-4">
          <img class="" src={imagem} alt="imagem {nome}" />
        </div>
          <!-- <div class="flex items-center justify-center">
            <h1 class="mr-3 items-center text-xl">Quantidade:</h1>
            <div class="flex items-center gap-3 text-center">
              <Button variant="buttonAdd" on:click={decrease}><Minus /></Button>
              <p class="text-xl font-bold">{quantidade}</p>
              <Button variant="buttonAdd" on:click={increase}><Plus /></Button>
            </div>
          </div> -->
        <p class="mt-3 text-right text-xl">
          Valor total: <span class="font-bold text-primary">R${precoTotal}</span
          >
        </p>
      </div>
      <Dialog.Footer>
        <Dialog.Close>
          <Button
            variant="buttonAdd"
            type="submit"
            class="flex items-center"
            on:click={addToCart}
          >
            <p class="pr-2">Adicionar produto ao pedido</p>
            <Plus />
          </Button>
        </Dialog.Close>
      </Dialog.Footer>
    </Dialog.Content>
  </Dialog.Root>
</main>
