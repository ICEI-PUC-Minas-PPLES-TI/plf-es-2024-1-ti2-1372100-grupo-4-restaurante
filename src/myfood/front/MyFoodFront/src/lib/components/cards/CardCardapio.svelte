<script lang="ts">
  import * as Card from "$lib/components/ui/card";
  import { Rating } from "flowbite-svelte";

  export let id: number;
  export let nome: string;
  export let preco: number;
  export let descricao: string;
  export let imagem: string;
  export let visibilidadeAvaliacao: boolean;
  export let mediaAvaliacao: number;
  export let avaliacoes: any[];
</script>

<Card.Root class="w-auto border shadow-lg">
  <div class="row flex flex-col-reverse sm:flex-row">
    <div class="column sm:w-7/12 w-full max-w-xl h-fit min-h-60">
      <Card.Header>
        <Card.Title
          class="mb-2 text-2xl font-bold tracking-tight text-gray-900 "
        >
          {nome}
        </Card.Title>
      </Card.Header>
      <Card.Content class="flex items-center">
        <div class="flex-grow">
          <p class="mb-3 font-normal leading-tight text-gray-700">
            Descrição: {descricao}
          </p>
          {#if visibilidadeAvaliacao == true}
            <div class="flex justify-center">
              <Rating
                count
                rating={parseFloat(mediaAvaliacao.toFixed(1))}
                id="example-4"
              >
                <span class="w-1 h-1 mx-1.5 bg-gray-500 rounded-full" />
                <p class="text-sm font-medium text-gray-900">
                  {#if avaliacoes.filter((av) => av.produto.id === id).length == 1}
                    {avaliacoes.filter((av) => av.produto.id === id).length}
                    avaliação
                  {:else}
                    {avaliacoes.filter((av) => av.produto.id === id).length}
                    avaliações
                  {/if}
                </p>
              </Rating>
            </div>
          {/if}
          <p class="text-2xl font-bold text-gray-900">R${preco}</p>
        </div>
      </Card.Content>
    </div>
    <div class="column sm:w-5/12 w-full min-h-60">
      <img
        src={imagem}
        alt=""
        class="ml-auto h-full sm:w-60 rounded-r-lg rounded-l-lg sm:rounded-l-none object-cover"
      />
    </div>
  </div>
</Card.Root>
