<script lang="ts">
  import type { PageData } from "./$types";
  import Button from '$lib/components/ui/button/button.svelte';
  import { ENDPOINT_URL } from '$lib/constants'
  
  export let data: PageData;

  interface Pedido {
    id: number;
    clienteIdPedido: {
      nomeCliente: string;
    };
    mesaIdPedido: {
      nomeMesa: string;
    };
    produtoId: {
      nome: string;
    };
    quantidade: number;
    status: 'PENDENTE' | 'PRONTO';
    valorPago: number;
  }

  let pedidos: Pedido[] = data.pedidos;

  async function marcarComoPronto(pedidoId: number) {
    try {
      const response = await fetch(`${ENDPOINT_URL}/pedidos/${pedidoId}/status`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ status: 'PRONTO' })
      });

      if (!response.ok) {
        throw new Error('Failed to update the status');
      }

      pedidos = pedidos.map(pedido =>
        pedido.id === pedidoId ? { ...pedido, status: 'PRONTO' } : pedido
      );
    } catch (error) {
      console.error('Error updating status:', error);
    }
  }
</script>

<main class="p-4">
  <h1 class="text-3xl font-bold mb-4 text-center">Pedidos</h1>
  {#if pedidos.length === 0}
    <p class="text-center">Nenhum pedido encontrado.</p>
  {:else}
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <div>
        <h2 class="text-xl font-semibold mb-2 text-center">Pedidos pendentes</h2>
        {#each pedidos.filter(pedido => pedido.status === 'PENDENTE') as pedido (pedido.id)}
          <div class="bg-white border border-gray-200 rounded shadow p-4 mb-4 flex justify-between">
            <div>
              <p><strong>Cliente:</strong> {pedido.clienteIdPedido.nomeCliente}</p>
              <p><strong>Mesa:</strong> {pedido.mesaIdPedido.nomeMesa}</p>
              <p><strong>Produto:</strong> {pedido.produtoId.nome}</p>
              <!-- <p><strong>Quantidade:</strong> {pedido.quantidade}</p> -->
              <p><strong>Status:</strong> {pedido.status}</p>
              <p><strong>Valor:</strong> {pedido.valorPago}</p>
            </div>
            <div>
              <Button variant="buttonAdd" on:click={() => marcarComoPronto(pedido.id)}>Pedido finalizado!</Button>
            </div>
          </div>
        {/each}
      </div>
      <div>
        <h2 class="text-xl font-semibold mb-2 text-center">Pedidos prontos</h2>
        <ul>
          {#each pedidos.filter(pedido => pedido.status === 'PRONTO') as pedido (pedido.id)}
            <li class="bg-white border border-gray-200 rounded shadow p-4 mb-4">
              <p><strong>Cliente:</strong> {pedido.clienteIdPedido.nomeCliente}</p>
              <p><strong>Mesa:</strong> {pedido.mesaIdPedido.nomeMesa}</p>
              <p><strong>Produto:</strong> {pedido.produtoId.nome}</p>
              <!-- <p><strong>Quantidade:</strong> {pedido.quantidade}</p> -->
              <p><strong>Status:</strong> {pedido.status}</p>
              <p><strong>Valor:</strong> {pedido.valorPago}</p>
            </li>
          {/each}
        </ul>
      </div>
    </div>
  {/if}
</main>
