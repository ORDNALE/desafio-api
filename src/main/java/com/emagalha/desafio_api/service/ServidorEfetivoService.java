package com.emagalha.desafio_api.service;

import com.emagalha.desafio_api.dto.*;
import com.emagalha.desafio_api.entity.Lotacao;
import com.emagalha.desafio_api.entity.ServidorEfetivo;
import com.emagalha.desafio_api.exception.ResourceNotFoundException;
import com.emagalha.desafio_api.repository.LotacaoRepository;
import com.emagalha.desafio_api.repository.PessoaRepository;
import com.emagalha.desafio_api.repository.ServidorEfetivoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.http.Method;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import java.security.NoSuchAlgorithmException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServidorEfetivoService {

    private final ServidorEfetivoRepository repository;
    private final LotacaoRepository lotacaoRepository;    
    private final PessoaRepository pessoaRepository;
    private final MinioClient minioClient;

    public List<ServidorEfetivoLotadoDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(servidor -> {
                    ServidorEfetivoLotadoDTO dto = new ServidorEfetivoLotadoDTO();
                    dto.setId(servidor.getId());
                    dto.setNome(servidor.getNome());
                    dto.setIdade(calcularIdade(servidor.getDataNascimento()));

                    // Obter a última unidade de lotação
                    if (servidor.getLotacoes() != null && !servidor.getLotacoes().isEmpty()) {
                        Lotacao ultimaLotacao = servidor.getLotacoes().get(servidor.getLotacoes().size() - 1);
                        dto.setUnidade(ultimaLotacao.getUnidade().getNome());
                    } else {
                        dto.setUnidade("Sem unidade lotada");
                    }

                    // Obter a última fotografia do servidor
                    if (servidor.getFotos() != null && !servidor.getFotos().isEmpty()) {
                        dto.setFotografia(servidor.getFotos().get(servidor.getFotos().size() - 1).getFotoBucket());
                    } else {
                        dto.setFotografia("Sem foto");
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ServidorEfetivo buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servidor com ID " + id + " não encontrado"));
    }

    public ServidorEfetivoDTO convertToDTO(ServidorEfetivo servidorEfetivo) {
        ServidorEfetivoDTO dto = new ServidorEfetivoDTO();
        dto.setPessoaId(servidorEfetivo.getId());
        dto.setMatricula(servidorEfetivo.getMatricula());
        return dto;
    }

    public ServidorEfetivo salvar(ServidorEfetivoDTO servidorDTO) {
        ServidorEfetivo servidor = new ServidorEfetivo();
        servidor.setId(servidorDTO.getPessoaId());
        servidor.setMatricula(servidorDTO.getMatricula());
        return repository.save(servidor);
    }

    public ServidorEfetivo atualizar(Integer id, ServidorEfetivoDTO servidorDTO) {
        ServidorEfetivo servidor = buscarPorId(id);
        servidor.setMatricula(servidorDTO.getMatricula());
        return repository.save(servidor);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Servidor com ID " + id + " não encontrado");
        }
        repository.deleteById(id);
    }

    public List<EnderecoFuncionalDTO> buscarEnderecoFuncional(String nome) {
        return pessoaRepository.findByNomeContaining(nome).stream()
                .flatMap(pessoa -> pessoa.getLotacoes().stream())
                .map(Lotacao::getUnidade)
                .flatMap(unidade -> unidade.getEnderecos().stream())
                .map(endereco -> {
                    EnderecoFuncionalDTO dto = new EnderecoFuncionalDTO();
                    dto.setLogradouro(endereco.getLogradouro());
                    dto.setNumero(endereco.getNumero());
                    dto.setBairro(endereco.getBairro());
                    dto.setCidade(endereco.getCidade().getNome());
                    dto.setUf(endereco.getCidade().getUf());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ServidorEfetivoLotadoDTO> buscarServidoresLotados(int unid_id) {
        return lotacaoRepository.findByUnidadeId(unid_id).stream()
                .map(Lotacao::getPessoa)
                .filter(pessoa -> pessoa.getServidorEfetivo() != null)
                .map(pessoa -> {
                    ServidorEfetivoLotadoDTO dto = new ServidorEfetivoLotadoDTO();
                    dto.setNome(pessoa.getNome());
                    dto.setIdade(calcularIdade(pessoa.getDataNascimento()));
                    dto.setUnidade(pessoa.getLotacoes().get(0).getUnidade().getNome());

                    if (!pessoa.getFotos().isEmpty()) {
                        dto.setFotografia(pessoa.getFotos().get(0).getFotoBucket());
                    } else {
                        dto.setFotografia(null);
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public String uploadFoto(Integer id, MultipartFile file) {
        ServidorEfetivo servidor = buscarPorId(id);
        String bucketName = "servidor-fotos";
        String objectName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        
        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );
            
            String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(5 * 60) // 5 minutos
                    .build()
            );
            
            return url;
        } catch (IOException | InvalidKeyException | ErrorResponseException | InsufficientDataException |
                 InternalException | InvalidResponseException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException("Erro ao fazer upload da foto", e);
        }
    }

    private int calcularIdade(Date dataNascimento) {
        if (dataNascimento == null) {
            return 0;
        }

        LocalDate dataNasc = dataNascimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hoje = LocalDate.now();
        return Period.between(dataNasc, hoje).getYears();
    }
}